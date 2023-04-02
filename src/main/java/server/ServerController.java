package server;

import common.Serializer;
import common.message.*;
import common.transportObjects.UserData;
import server.userDataBase.UserDataBase;
import server.userDataBase.UserDataBaseRecord;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

import static common.CipherConfig.CipherType.EBC;
import static common.ConnectionConfig.PORT;
import static common.message.MessageType.AUTHORIZATION;
import static common.message.MessageType.USER_CONNECTION;
import static common.transportObjects.UserDataProcessResponseType.*;

public class ServerController {

    private static ServerController serverController;

    private final List<ClientHandler> clientHandlers;
    private final ServerSocket socket;
    private final Map<Long, Long> clientIdToUserIdMap;
    private final UserDataBase userDataBase;

    public static void main(String[] args) {
        getServerController();
    }

    private ServerController() {
        try {
            this.socket = new ServerSocket(PORT);
            this.clientIdToUserIdMap = new HashMap<>();
            this.clientHandlers = new ArrayList<>();
            this.userDataBase = new UserDataBase();
            handleClients();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ServerController getServerController() {
        if (serverController == null) {
            serverController = new ServerController();
        }
        return serverController;
    }

    public void handleMessage(ClientHandler clientHandler, Message message) {
        try {
            //decrypt
            byte[] decryptedMessageType = message.getMessageType();
            byte[] decryptedContent = message.getContent();
            MessageType messageType = Serializer.deserialize(decryptedMessageType);
            switch (messageType) {
                case REGISTRATION_REQUEST -> processRegistrationMessage(clientHandler, decryptedContent);
                case LOGIN_REQUEST -> processLoginMessage(clientHandler, decryptedContent);
                case ALL_USER_CONNECTION_REQUEST -> processAllUserConnectionMessage(clientHandler);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void sendMessageToClient(ClientHandler clientHandler, MessageType messageType, Object content) {
        try {
            //encrypt
            byte[] encryptedMessageType = Serializer.serialize(messageType);
            byte[] encryptedContent = Serializer.serialize(content);
            Message message = new Message(clientHandler.getClientId(), EBC, encryptedMessageType, encryptedContent);
            clientHandler.sendMessageToClient(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void broadcastMessage(ClientHandler clientHandler, MessageType messageType, Object content, boolean authorized) {
        try {
            //encrypt
            byte[] encryptedMessageType = Serializer.serialize(messageType);
            byte[] encryptedContent = Serializer.serialize(content);
            for (ClientHandler otherClientHandler : this.clientHandlers) {
                if (otherClientHandler != clientHandler && (!authorized || this.clientIdToUserIdMap.get(otherClientHandler.getClientId()) != null)) {
                    Message message = new Message(otherClientHandler.getClientId(), EBC, encryptedMessageType, encryptedContent);
                    otherClientHandler.sendMessageToClient(message);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Long mapClientIdToUserId(Long clientId) {
        return this.clientIdToUserIdMap.get(clientId);
    }

    public synchronized void removeClientIdFromMap(Long clientId) {
        this.clientIdToUserIdMap.remove(clientId);
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

    private void connectUser(ClientHandler clientHandler, UserData userData) {
        this.clientIdToUserIdMap.put(clientHandler.getClientId(), userData.getId());
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
