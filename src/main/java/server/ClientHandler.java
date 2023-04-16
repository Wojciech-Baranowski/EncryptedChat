package server;

import app.encryption.aesCipher.CipherType;
import common.Serializer;
import common.message.Message;
import common.message.UserDisconnectionMessage;
import common.transportObjects.UserData;
import lombok.Getter;
import lombok.Setter;
import server.userDataBase.UserDataBaseRecord;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

import static app.encryption.aesCipher.CipherType.ECB;
import static common.message.MessageType.USER_DISCONNECTION;

public class ClientHandler implements Runnable {

    private final ServerController serverController;
    private final Socket socket;
    private final ObjectInputStream reader;
    private final ObjectOutputStream writer;
    @Getter
    @Setter
    private Long clientId;

    public ClientHandler(ServerController serverController, Socket socket, Long clientId) throws IOException {
        this.serverController = serverController;
        this.socket = socket;
        this.writer = new ObjectOutputStream(socket.getOutputStream());
        this.reader = new ObjectInputStream(socket.getInputStream());
        this.clientId = clientId;
        getClientHandlers().add(this);
    }

    @Override
    public void run() {
        routeMessages();
    }

    private void routeMessages() {
        while (this.socket != null && this.socket.isConnected() && !this.socket.isClosed()) {
            try {
                byte[] encryptedMessage = (byte[]) this.reader.readObject();
                byte[] decryptedMessage = decryptMessage(this.clientId, encryptedMessage, ECB);
                Message message = Serializer.deserialize(decryptedMessage);
                if (message.getReceiverId() == null) {
                    this.serverController.handleMessage(this, message);
                } else {
                    Long receiverClientId = this.serverController.mapUserIdToClientId(message.getReceiverId());
                    message.setReceiverId(receiverClientId);
                    sendMessageToClient(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                disconnectUser();
                closeSession();
            }
        }
    }

    public void sendMessageToClient(Message message) {
        try {
            ClientHandler receiver = getClientHandlers().stream()
                    .filter(c -> Objects.equals(c.getClientId(), message.getReceiverId()))
                    .findFirst()
                    .orElse(null);
            if (receiver != null) {
                byte[] byteMessage = Serializer.serialize(message);
                byte[] encryptedMessage = encryptMessage(receiver.getClientId(), byteMessage);
                receiver.writer.writeObject(encryptedMessage);
            }
        } catch (Exception e) {
            //silenced
        }
    }

    private byte[] decryptMessage(Long senderId, byte[] encryptedMessage, CipherType cipherType) {
        return encryptedMessage;
        /*if(Aes.getSessionKeyBySessionPartnerId(senderId) != null) {
            return Aes.decrypt(senderId, encryptedMessage, (cipherType != null) ? cipherType : ECB);
        } else if(Rsa.getPublicKeyBySessionPartnerId(senderId) != null){
            return Rsa.decryptMessage(encryptedMessage);
        } else {
            return encryptedMessage;
        }*/
    }

    private byte[] encryptMessage(Long receiverId, byte[] message) {
        return message;
        /*if(Aes.getSessionKeyBySessionPartnerId(receiverId) != null) {
            CipherType cipherType = receiverId == null ? ECB : getChatButtonController().getCipherType();
            return Aes.encrypt(receiverId, message, cipherType);
        } else if(Rsa.getPublicKeyBySessionPartnerId(receiverId) != null) {
            Key publicKey = Rsa.getPublicKeyBySessionPartnerId(receiverId);
            return Rsa.encryptMessage(message, publicKey);
        } else {
            return message;
        }*/
    }

    private void disconnectUser() {
        if (getClientHandlers().contains(this)) {
            getClientHandlers().remove(this);
            Long userId = this.serverController.mapClientIdToUserId(this.clientId);
            this.serverController.removeClientIdFromMap(this.clientId);
            this.serverController.removeUserIdFromMap(userId);
            UserDataBaseRecord userDataBaseRecord = this.serverController.getUserDataBase().findUserDataBaseRecordByUserId(userId);
            UserData userData = new UserData(userDataBaseRecord.getId(), userDataBaseRecord.getUserName());
            UserDisconnectionMessage userDisconnectionMessage = new UserDisconnectionMessage(userData);
            this.serverController.broadcastMessage(this, USER_DISCONNECTION, userDisconnectionMessage, true);
        }
    }

    private void closeSession() {
        try {
            if (this.reader != null && this.socket != null && !this.socket.isClosed()) {
                this.reader.close();
            }
            if (this.writer != null && this.socket != null && !this.socket.isClosed()) {
                this.writer.close();
            }
            if (this.socket != null && !this.socket.isClosed()) {
                this.socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<ClientHandler> getClientHandlers() {
        return this.serverController.getClientHandlers();
    }

}
