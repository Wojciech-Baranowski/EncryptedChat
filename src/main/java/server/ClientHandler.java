package server;

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
                //decryptServ
                byte[] message = (byte[]) this.reader.readObject();
                Message decryptedMessage = Serializer.deserialize(message);
                if (decryptedMessage.getReceiverId() == null) {
                    this.serverController.handleMessage(this, decryptedMessage);
                } else {
                    sendMessageToClient(decryptedMessage);
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
                //encryptServ
                byte[] encryptedMessage = Serializer.serialize(message);
                receiver.writer.writeObject(encryptedMessage);
            }
        } catch (Exception e) {
            disconnectUser();
            closeSession();
        }
    }

    private void disconnectUser() {
        if (getClientHandlers().contains(this)) {
            getClientHandlers().remove(this);
            Long userId = this.serverController.mapClientIdToUserId(this.clientId);
            this.serverController.removeClientIdFromMap(this.clientId);
            UserDataBaseRecord userDataBaseRecord = this.serverController.getUserDataBase().findUserDataBaseRecordByUserId(userId);
            UserData userData = new UserData(userDataBaseRecord.getId(), userDataBaseRecord.getUserName());
            UserDisconnectionMessage userDisconnectionMessage = new UserDisconnectionMessage(userData);
            this.serverController.broadcastMessage(this, USER_DISCONNECTION, userDisconnectionMessage, true);
        }
    }

    private void closeSession() {
        try {
            if (this.reader != null)
                this.reader.close();
            if (this.writer != null)
                this.writer.close();
            if (this.socket != null)
                this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<ClientHandler> getClientHandlers() {
        return this.serverController.getClientHandlers();
    }

}
