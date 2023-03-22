package app.controllers;

import common.message.MessageType;

public class LoginController {

    private final MainController mainController;

    public LoginController(MainController mainController) {
        this.mainController = mainController;
    }

    public void routeMessage(MessageType messageType, byte[] content) {

    }

}
