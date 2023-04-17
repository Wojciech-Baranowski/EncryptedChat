package server;

import common.EncryptionType;
import common.Serializer;
import common.encryption.Aes;
import common.encryption.Rsa;
import common.encryption.Sha256;
import common.message.*;
import common.transportObjects.UserData;
import server.userDataBase.UserDataBase;
import server.userDataBase.UserDataBaseRecord;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

import static common.ConnectionConfig.PORT;
import static common.EncryptionType.AES;
import static common.EncryptionType.NONE;
import static common.encryption.aesCipher.CipherType.ECB;
import static common.message.MessageType.*;
import static common.transportObjects.UserDataProcessResponseType.*;

public class ServerController {

    private static ServerController serverController;

    private final List<ClientHandler> clientHandlers;
    private final ServerSocket socket;
    private final Map<Long, Long> clientIdToUserIdMap;
    private final Map<Long, Long> userIdToCilentIdMap;
    private final UserDataBase userDataBase;

    public static void main(String[] args) {
        getServerController(args[0]);
    }

    private ServerController(String keyPassword) {
        try {
            this.socket = new ServerSocket(PORT);
            this.clientIdToUserIdMap = new HashMap<>();
            this.userIdToCilentIdMap = new HashMap<>();
            this.clientHandlers = new ArrayList<>();
            this.userDataBase = new UserDataBase();
            byte[] keyPasswordHash = Sha256.hash(keyPassword);
            if (Rsa.isKeyValid(keyPasswordHash)) {
                Rsa.initialize(keyPasswordHash);
                handleClients();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ServerController getServerController(String keyPassword) {
        if (serverController == null) {
            serverController = new ServerController(keyPassword);
        }
        return serverController;
    }

    public void handleMessage(ClientHandler clientHandler, Message message) {
        try {
            MessageType messageType = Serializer.deserialize(message.getMessageType());
            switch (messageType) {
                case REGISTRATION_REQUEST -> processRegistrationMessage(clientHandler, message.getContent());
                case LOGIN_REQUEST -> processLoginMessage(clientHandler, message.getContent());
                case ALL_USER_CONNECTION_REQUEST -> processAllUserConnectionMessage(clientHandler);
                case SERVER_HANDSHAKE -> processHandshakeMessage(clientHandler, message.getContent());
                case SERVER_SESSION -> processSessionMessage(clientHandler, message.getContent());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void sendMessageToClient(ClientHandler clientHandler, MessageType messageType, Object content, EncryptionType encryptionType) {
        try {
            byte[] byteMessageType = Serializer.serialize(messageType);
            byte[] byteEncryptedContent = Serializer.serialize(content);
            Message message = new Message(clientHandler.getClientId(), null, ECB, byteMessageType, byteEncryptedContent);
            clientHandler.sendMessageToClient(message, encryptionType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void sendMessageToClient(ClientHandler clientHandler, MessageType messageType, Object content) {
        sendMessageToClient(clientHandler, messageType, content, AES);
    }

    public synchronized void broadcastMessage(ClientHandler clientHandler, MessageType messageType, Object content, boolean authorized, EncryptionType encryptionType) {
        try {
            byte[] byteMessageType = Serializer.serialize(messageType);
            byte[] byteContent = Serializer.serialize(content);
            for (ClientHandler otherClientHandler : this.clientHandlers) {
                if (otherClientHandler != clientHandler && (!authorized || this.clientIdToUserIdMap.get(otherClientHandler.getClientId()) != null)) {
                    Message message = new Message(otherClientHandler.getClientId(), null, ECB, byteMessageType, byteContent);
                    otherClientHandler.sendMessageToClient(message, encryptionType);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void broadcastMessage(ClientHandler clientHandler, MessageType messageType, Object content, boolean authorized) {
        broadcastMessage(clientHandler, messageType, content, authorized, AES);
    }

    public void processRegistrationMessage(ClientHandler clientHandler, byte[] content) {
        try {
            AuthorizationMessage authorizationMessage;
            RegistrationMessage registrationMessage = Serializer.deserialize(content);
            if (this.userDataBase.findUserDataBaseRecordByUserName(registrationMessage.getUserName()) == null) {
                this.userDataBase.addRecord(registrationMessage.getUserName(), registrationMessage.getPassword());
                authorizationMessage = new AuthorizationMessage(REGISTRATION_SUCCESS, null);
            } else {
                authorizationMessage = new AuthorizationMessage(REGISTRATION_FAILED_USERNAME_ALREADY_EXIST, null);
            }
            sendMessageToClient(clientHandler, AUTHORIZATION, authorizationMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void processLoginMessage(ClientHandler clientHandler, byte[] content) {
        try {
            AuthorizationMessage authorizationMessage;
            LoginMessage loginMessage = Serializer.deserialize(content);
            if (this.userDataBase.findUserDataBaseRecordByUserName(loginMessage.getUserName()) != null) {
                UserDataBaseRecord dataBaseRecord = this.userDataBase.findUserDataBaseRecordByUserName(loginMessage.getUserName());
                if (loginMessage.getPassword().equals(dataBaseRecord.getPassword())) {
                    UserData userData = new UserData(dataBaseRecord.getId(), dataBaseRecord.getUserName());
                    connectUser(clientHandler, userData);
                    authorizationMessage = new AuthorizationMessage(LOGIN_SUCCESS, userData);
                } else {
                    authorizationMessage = new AuthorizationMessage(LOGIN_FAILED_INCORRECT_PASSWORD, null);
                }
            } else {
                authorizationMessage = new AuthorizationMessage(LOGIN_FAILED_USERNAME_DOES_NOT_EXIST, null);
            }
            sendMessageToClient(clientHandler, AUTHORIZATION, authorizationMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void processAllUserConnectionMessage(ClientHandler clientHandler) {
        for (ClientHandler otherClientHandler : this.clientHandlers) {
            if (otherClientHandler != clientHandler) {
                Long otherUserId = this.clientIdToUserIdMap.get(otherClientHandler.getClientId());
                if (this.clientIdToUserIdMap.get(otherClientHandler.getClientId()) != null) {
                    UserDataBaseRecord dataBaseRecord = this.userDataBase.findUserDataBaseRecordByUserId(otherUserId);
                    UserData otherUserData = new UserData(dataBaseRecord.getId(), dataBaseRecord.getUserName());
                    UserConnectionMessage userConnectionMessage = new UserConnectionMessage(otherUserData);
                    sendMessageToClient(clientHandler, USER_CONNECTION, userConnectionMessage);
                }
            }
        }
    }

    public void processHandshakeMessage(ClientHandler clientHandler, byte[] content) {
        ServerHandshakeMessage handshakeMessage = Serializer.deserialize(content);
        Rsa.addPublicKeyBySessionPartnerId(clientHandler.getClientId(), handshakeMessage.getPublicKey());
        ServerHandshakeMessage serverHandshakeMessage = new ServerHandshakeMessage(Rsa.getPublicKey());
        sendMessageToClient(clientHandler, SERVER_HANDSHAKE, serverHandshakeMessage, NONE);
    }

    public void processSessionMessage(ClientHandler clientHandler, byte[] content) {
        ServerSessionMessage sessionMessage = Serializer.deserialize(content);
        Aes.sessionInitialize(clientHandler.getClientId(), sessionMessage.getSessionKey(), sessionMessage.getInitialVector());
    }

    public synchronized Long mapClientIdToUserId(Long clientId) {
        return this.clientIdToUserIdMap.get(clientId);
    }

    public synchronized void removeClientIdFromMap(Long clientId) {
        this.clientIdToUserIdMap.remove(clientId);
    }

    public synchronized Long mapUserIdToClientId(Long userId) {
        return this.userIdToCilentIdMap.get(userId);
    }

    public synchronized void removeUserIdFromMap(Long userId) {
        this.userIdToCilentIdMap.remove(userId);
    }

    public synchronized List<ClientHandler> getClientHandlers() {
        return this.clientHandlers;
    }

    public synchronized UserDataBase getUserDataBase() {
        return this.userDataBase;
    }

    private void handleClients() {
        while (!this.socket.isClosed()) {
            try {
                addClient();
            } catch (IOException e) {
                closeServerSocket();
                throw new RuntimeException(e);
            }
        }
        closeServerSocket();
    }

    private void addClient() throws IOException {
        ClientHandler clientHandler = new ClientHandler(this, this.socket.accept(), new Random().nextLong());
        new Thread(clientHandler).start();
    }

    private void connectUser(ClientHandler clientHandler, UserData userData) {
        this.clientIdToUserIdMap.put(clientHandler.getClientId(), userData.getId());
        this.userIdToCilentIdMap.put(userData.getId(), clientHandler.getClientId());
        UserConnectionMessage userConnectionMessage = new UserConnectionMessage(userData);
        broadcastMessage(clientHandler, USER_CONNECTION, userConnectionMessage, true);
    }

    private void closeServerSocket() {
        try {
            if (this.socket != null) {
                this.socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
