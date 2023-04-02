package app.connection;

import common.Serializer;
import common.message.ConfirmationMessage;
import common.message.MessageType;
import common.message.UserConnectionMessage;
import common.message.UserDisconnectionMessage;

import static app.services.FileService.getFileService;
import static app.services.UserService.getUserService;
import static common.message.MessageType.ALL_USER_CONNECTION_REQUEST;
import static common.message.MessageType.CONFIRMATION;

public class ChatConnectionController {

    private final ConnectionController connectionController;

    public ChatConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    public void routeMessage(MessageType messageType, byte[] content) {
        switch (messageType) {
            case USER_CONNECTION -> processConnectionMessage(content);
            case USER_DISCONNECTION -> processDisconnectionMessage(content);
            case CONFIRMATION -> processConfirmationMessage(content);
        }
    }

    public void prepareAndSendAllUserConnectionRequestMessage() {
        this.connectionController.sendMessage(ALL_USER_CONNECTION_REQUEST, null);
    }

    public void prepareAndSendConfirmationMessage(int fragmentNumber, Long receiverId) {
        ConfirmationMessage confirmationMessage = new ConfirmationMessage(fragmentNumber);
        this.connectionController.sendMessage(CONFIRMATION, confirmationMessage, receiverId);
    }

    private void processConnectionMessage(byte[] content) {
        try {
            UserConnectionMessage userConnectionMessage = Serializer.deserialize(content);
            getUserService().addReceiver(userConnectionMessage.getUserData().getId(), userConnectionMessage.getUserData().getUserName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processDisconnectionMessage(byte[] content) {
        try {
            UserDisconnectionMessage userDisconnectionMessage = Serializer.deserialize(content);
            getUserService().removeReceiver(userDisconnectionMessage.getUserData().getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processConfirmationMessage(byte[] content) {
        try {
            ConfirmationMessage confirmationMessage = Serializer.deserialize(content);
            getFileService().receiveConfirmation(confirmationMessage.getConfirmedFragmentId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
