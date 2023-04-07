package app.connection;

import common.Serializer;
import common.message.*;

import java.util.ArrayList;
import java.util.List;

import static app.services.FileService.getFileService;
import static app.services.UserService.getUserService;
import static common.message.MessageType.*;

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
            case TEXT -> processTextMessage(content);
            case FILE -> processFileMessage(content);
        }
    }

    public void prepareAndSendAllUserConnectionRequestMessage() {
        this.connectionController.sendMessage(ALL_USER_CONNECTION_REQUEST, null);
    }

    public void prepareAndSendConfirmationMessage(int fragmentNumber, Long receiverId) {
        ConfirmationMessage confirmationMessage = new ConfirmationMessage(fragmentNumber);
        this.connectionController.sendMessage(CONFIRMATION, confirmationMessage, receiverId);
    }

    public void prepareAndSendTextMessage(String text, Long senderId, Long receiverId) {
        TextMessage textMessage = new TextMessage(senderId, text);
        this.connectionController.sendMessage(TEXT, textMessage, receiverId);
    }

    public void prepareAndSendFileMessage(Long senderId, List<byte[]> fileFragments, Long receiverId) {
        List<Object> fileMessageList = new ArrayList<>();
        for (int i = 0; i < fileFragments.size(); i++) {
            FileMessage fileMessage = new FileMessage(senderId, i, fileFragments.size(), fileFragments.get(i));
            fileMessageList.add(fileMessage);
        }
        this.connectionController.sendMessages(FILE, fileMessageList, receiverId);
    }

    private void processConnectionMessage(byte[] content) {
        try {
            UserConnectionMessage userConnectionMessage = Serializer.deserialize(content);
            getUserService().addReceiver(userConnectionMessage.getUserData());
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

    private void processTextMessage(byte[] content) {
        try {
            TextMessage textMessage = Serializer.deserialize(content);
            getFileService().receiveText(textMessage.getSenderId(), textMessage.getText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processFileMessage(byte[] content) {
        try {
            FileMessage fileMessage = Serializer.deserialize(content);
            getFileService().receiveFileFragment(fileMessage.getSenderId(), fileMessage.getFileFragmentNumber(), fileMessage.getNumberOfFileFragments(), fileMessage.getFileFragment());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
