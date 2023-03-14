package server;

import common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {

    private static final List<ClientHandler> clientHandlers = new ArrayList<>();
    private final int clientId;
    private final Socket socket;
    private final ObjectInputStream reader;
    private final ObjectOutputStream writer;

    public ClientHandler(Socket socket, int clientId) throws IOException {
        this.clientId = clientId;
        this.socket = socket;
        this.writer = new ObjectOutputStream(socket.getOutputStream());
        this.reader = new ObjectInputStream(socket.getInputStream());
        clientHandlers.add(this);
    }

    @Override
    public void run() {
        routeMessages();
    }

    private void routeMessages() {
        while (socket.isConnected()) {
            try {
                Message message = (Message) reader.readObject();
                sendMessageToClient(message);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                closeSession();
            }
        }
    }

    private void sendMessageToClient(Message message) throws IOException {
        ClientHandler receiver = clientHandlers.stream()
                .filter(c -> c.clientId == message.getReceiverId())
                .findFirst()
                .orElseThrow();
        receiver.writer.writeObject(message);
    }

    private void closeSession() {
        clientHandlers.remove(this);
        try {
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
