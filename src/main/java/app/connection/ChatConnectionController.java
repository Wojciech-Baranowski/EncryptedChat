package app.connection;

import common.CipherConfig;
import common.message.MessageType;

public class ChatConnectionController {

    private final ConnectionController connectionController;

    public ChatConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    public void routeMessage(MessageType messageType, CipherConfig.CipherType cipherType, byte[] content) {

    }

}
