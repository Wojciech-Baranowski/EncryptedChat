package app.connection;

import common.Serializer;
import common.message.MessageType;
import common.message.UserConnectionMessage;
import common.message.UserDisconnectionMessage;

import static app.services.UserService.getUserService;
import static common.message.MessageType.ALL_USER_CONNECTION_REQUEST;

public class ChatConnectionController {

    private final ConnectionController connectionController;

    public ChatConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    public void routeMessage(MessageType messageType, byte[] content) {
        switch (messageType) {
            case USER_CONNECTION -> processConnectionMessage(content);
            case USER_DISCONNECTION -> processDisconnectionMessage(content);
        }
    }

    private void processConnectionMessage(byte[] content) {
        UserConnectionMessage userConnectionMessage = Serializer.deserialize(content);
        getUserService().addReceiver(userConnectionMessage.getUserData().getId(), userConnectionMessage.getUserData().getUserName());
    }

    private void processDisconnectionMessage(byte[] content) {
        UserDisconnectionMessage userDisconnectionMessage = Serializer.deserialize(content);
        getUserService().removeReceiver(userDisconnectionMessage.getUserData().getId());
    }

    public void prepareAndSendAllUserConnectionRequestMessage() {
        this.connectionController.sendMessage(ALL_USER_CONNECTION_REQUEST, null);
    }

}
