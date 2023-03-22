package server;

import common.message.Message;
import server.userDataBase.UserDataBase;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static common.ConnectionConfig.PORT;

public class ServerController {

    private static ServerController serverController;

    private final ServerSocket socket;
    private final UserDataBase userDataBase;
    private final Map<Long, Integer> clientToUserMap;

    public static void main(String[] args) {
        getServerController();
    }

    private ServerController() {
        try {
            this.socket = new ServerSocket(PORT);
            this.userDataBase = new UserDataBase();
            this.clientToUserMap = new HashMap<>();
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

    public void handleMessage(Message message) {

    }

    private void handleClients() {
        while (!socket.isClosed()) {
            try {
                addClient();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeServerSocket();
            }
        }
    }

    private void addClient() throws IOException {
        ClientHandler clientHandler = new ClientHandler(socket.accept(), new Random().nextLong());
        Thread thread = new Thread(clientHandler);
        thread.start();
    }

    private void closeServerSocket() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
