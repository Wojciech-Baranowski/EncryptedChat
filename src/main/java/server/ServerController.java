package server;

import common.Serializer;
import common.message.*;
import common.transportObjects.UserData;
import lombok.Getter;
import server.userDataBase.UserDataBase;
import server.userDataBase.UserDataBaseRecord;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

import static common.ConnectionConfig.PORT;
import static common.message.MessageType.AUTHORIZATION;
import static common.transportObjects.UserDataProcessResponseType.*;

public class ServerController {

    @Getter
    private final List<ClientHandler> clientHandlers;
    private final ServerSocket socket;
    private final UserDataBase userDataBase;
    private final Map<Long, Long> userIdToClientIdMap;

    public static void main(String[] args) {
        new ServerController();
    }

    private ServerController() {
        try {
            this.socket = new ServerSocket(PORT);
            this.userDataBase = new UserDataBase();
            this.userIdToClientIdMap = new HashMap<>();
            this.clientHandlers = new ArrayList<>();
            handleClients();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleMessage(ClientHandler clientHandler, Message message) {
        //decrypt
        byte[] decryptedMessageType = message.getMessageType();
        byte[] decryptedContent = message.getContent();
        MessageType messageType = Serializer.deserialize(decryptedMessageType);
        switch (messageType) {
            case REGISTRATION -> {
                AuthorizationMessage authorizationMessage = processRegistrationMessage(decryptedContent);
                sendMessageToClient(clientHandler, AUTHORIZATION, Serializer.serialize(authorizationMessage));
            }
            case LOGIN -> {
                AuthorizationMessage authorizationMessage = processLoginMessage(decryptedContent);
                sendMessageToClient(clientHandler, AUTHORIZATION, Serializer.serialize(authorizationMessage));
            }
        }
    }

    public Long mapUserIdToClientId(Long userId) {
        return this.userIdToClientIdMap.get(userId);
    }

    private void sendMessageToClient(ClientHandler clientHandler, MessageType messageType, byte[] content) {
        //encrypt
        byte[] encryptedMessageType = Serializer.serialize(messageType);
        byte[] encryptedContent = content;
        Message message = new Message(clientHandler.getClientId(), null, encryptedMessageType, encryptedContent);
        clientHandler.sendMessageToClient(message);
    }

    private AuthorizationMessage processRegistrationMessage(byte[] content) {
        RegistrationMessage registrationMessage = Serializer.deserialize(content);
        if (this.userDataBase.findUserDataBaseRecordByUserName(registrationMessage.getUserName()) == null) {
            this.userDataBase.addRecord(registrationMessage.getUserName(), registrationMessage.getPassword());
            return new AuthorizationMessage(REGISTRATION_SUCCESS, null);
        } else {
            return new AuthorizationMessage(REGISTRATION_FAILED_USERNAME_ALREADY_EXIST, null);
        }
    }

    private AuthorizationMessage processLoginMessage(byte[] content) {
        LoginMessage loginMessage = Serializer.deserialize(content);
        if (this.userDataBase.findUserDataBaseRecordByUserName(loginMessage.getUserName()) != null) {
            UserDataBaseRecord dataBaseRecord = this.userDataBase.findUserDataBaseRecordByUserName(loginMessage.getUserName());
            if (loginMessage.getPassword().equals(dataBaseRecord.getPassword())) {
                UserData userData = new UserData(dataBaseRecord.getId(), dataBaseRecord.getUserName());
                return new AuthorizationMessage(LOGIN_SUCCESS, userData);
            } else {
                return new AuthorizationMessage(LOGIN_FAILED_INCORRECT_PASSWORD, null);
            }
        } else {
            return new AuthorizationMessage(LOGIN_FAILED_USERNAME_DOES_NOT_EXIST, null);
        }
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
        Thread thread = new Thread(clientHandler);
        thread.start();
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
