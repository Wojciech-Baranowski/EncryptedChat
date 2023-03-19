package server;

import server.dataBase.DataBase;

import java.io.IOException;
import java.net.ServerSocket;

import static common.ConnectionConfig.PORT;

public class ServerController {

    private final ServerSocket socket;
    private final DataBase dataBase;

    public static void main(String[] args) throws IOException {
        new ServerController();
    }

    private ServerController() throws IOException {
        this.socket = new ServerSocket(PORT);
        this.dataBase = new DataBase();
        handleClients();
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
        /*ClientHandler clientHandler = new ClientHandler(socket.accept(), clientIdSequence.getNextId());
        Thread thread = new Thread(clientHandler);
        thread.start();*/
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
