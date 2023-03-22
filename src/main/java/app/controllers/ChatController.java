package app.controllers;

import common.CipherConfig;
import common.message.MessageType;

public class ChatController {

    private final MainController mainController;

    public ChatController(MainController mainController) {
        this.mainController = mainController;
    }

    public void routeMessage(MessageType messageType, CipherConfig.CipherType cipherType, byte[] content) {

    }

}
