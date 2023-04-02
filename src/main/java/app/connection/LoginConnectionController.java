package app.connection;

import common.Serializer;
import common.message.AuthorizationMessage;
import common.message.LoginMessage;
import common.message.MessageType;
import common.message.RegistrationMessage;

import static app.services.UserService.getUserService;
import static common.message.MessageType.LOGIN_REQUEST;
import static common.message.MessageType.REGISTRATION_REQUEST;

public class LoginConnectionController {

    private final ConnectionController connectionController;

    public LoginConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    public void routeMessage(MessageType messageType, byte[] content) {
        switch (messageType) {
            case AUTHORIZATION -> processAuthorizationMessage(content);
        }
    }

    public void prepareAndSendRegisterRequestMessage(String userName, String passwordHash) {
        RegistrationMessage registrationMessage = new RegistrationMessage(userName, passwordHash);
        this.connectionController.sendMessage(REGISTRATION_REQUEST, registrationMessage, null);
    }

    public void prepareAndSendLoginRequestMessage(String userName, String passwordHash) {
        LoginMessage loginMessage = new LoginMessage(userName, passwordHash);
        this.connectionController.sendMessage(LOGIN_REQUEST, loginMessage, null);
    }

    private void processAuthorizationMessage(byte[] content) {
        try {
            AuthorizationMessage authorizationMessage = Serializer.deserialize(content);
            getUserService().processResponse(authorizationMessage.getUserDataProcessResponseType(), authorizationMessage.getUserData());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
