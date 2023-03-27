package server;

import common.message.Message;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

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
        while (socket.isConnected()) {
            try {
                Message message = (Message) this.reader.readObject();
                message.setReceiverId(mapUserIdToClientId(message.getReceiverId()));
                if (message.getReceiverId() == null) {
                    this.serverController.handleMessage(this, message);
                } else {
                    sendMessageToClient(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                closeSession();
                throw new RuntimeException(e);
            }
        }
        closeSession();
    }

    public void sendMessageToClient(Message message) {
        try {
            ClientHandler receiver = getClientHandlers().stream()
                    .filter(c -> Objects.equals(c.getClientId(), message.getReceiverId()))
                    .findFirst()
                    .orElse(null);
            if (receiver != null) {
                receiver.writer.writeObject(message);
            }
        } catch (Exception e) {
            closeSession();
            throw new RuntimeException(e);
        }
    }

    private void closeSession() {
        getClientHandlers().remove(this);
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

    private Long mapUserIdToClientId(Long userId) {
        return this.serverController.mapUserIdToClientId(userId);
    }

}
