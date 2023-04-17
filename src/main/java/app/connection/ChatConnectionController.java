package app.connection;

import common.Serializer;
import common.encryption.Aes;
import common.encryption.Rsa;
import common.encryption.aesCipher.InitialVector;
import common.encryption.rsaKey.Key;
import common.message.*;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

import static app.services.FileService.getFileService;
import static app.services.UserService.getUserService;
import static common.EncryptionType.NONE;
import static common.EncryptionType.RSA;
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
            case CLIENT_HANDSHAKE -> processHandshakeMessage(content);
            case CLIENT_SESSION -> processSessionMessage(content);
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

    public void prepareAndSendHandshakeMessage(Long senderId, Key publicKey, Long receiverId, boolean returning) {
        HandshakeMessage handshakeMessage = new HandshakeMessage(senderId, publicKey, returning);
        this.connectionController.sendMessage(CLIENT_HANDSHAKE, handshakeMessage, receiverId, NONE);
    }

    public void prepareAndSendSessionMessage(Long senderId, Long receiverId) {
        Aes.sessionInitialize(receiverId);
        SecretKey sessionKey = Aes.getSessionKeyBySessionPartnerId(receiverId);
        InitialVector initialVector = Aes.getInitialVectorBySessionPartnerId(receiverId);
        SessionMessage sessionMessage = new SessionMessage(senderId, sessionKey, initialVector);
        this.connectionController.sendMessage(CLIENT_SESSION, sessionMessage, receiverId, RSA);
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
            Aes.sessionDestroy(userDisconnectionMessage.getUserData().getId());
            Rsa.removePublicKeyBySessionPartnerId(userDisconnectionMessage.getUserData().getId());
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

    private void processHandshakeMessage(byte[] content) {
        HandshakeMessage handshakeMessage = Serializer.deserialize(content);
        Long senderId = handshakeMessage.getSenderId();
        Rsa.addPublicKeyBySessionPartnerId(senderId, handshakeMessage.getPublicKey());
        if (handshakeMessage.isReturning()) {
            prepareAndSendSessionMessage(getUserService().getUserId(), senderId);
        } else {
            prepareAndSendHandshakeMessage(getUserService().getUserId(), Rsa.getPublicKey(), senderId, true);
        }
    }

    private void processSessionMessage(byte[] content) {
        SessionMessage sessionMessage = Serializer.deserialize(content);
        Aes.sessionInitialize(sessionMessage.getSenderId(), sessionMessage.getSessionKey(), sessionMessage.getInitialVector());
    }

}
