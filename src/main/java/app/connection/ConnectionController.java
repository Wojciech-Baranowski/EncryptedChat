package app.connection;

import app.engine.listener.ParallelThread;
import app.engine.listener.SynchronizedCollection;
import app.engine.scene.SceneBean;
import common.ConnectionConfig;
import common.EncryptionType;
import common.Serializer;
import common.encryption.Aes;
import common.encryption.Rsa;
import common.encryption.aesCipher.CipherType;
import common.encryption.rsaKey.Key;
import common.message.Message;
import common.message.MessageType;
import lombok.Getter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static app.gui.chat.buttons.ChatButtonController.getChatButtonController;
import static app.services.UserService.getUserService;
import static common.EncryptionType.AES;
import static common.EncryptionType.RSA;
import static common.encryption.aesCipher.CipherType.ECB;

public class ConnectionController {

    private static ConnectionController connectionController;
    @Getter
    private static ChatConnectionController chatConnectionController;
    @Getter
    private static LoginConnectionController loginConnectionController;

    private final Socket socket;
    private final ObjectInputStream reader;
    private final ObjectOutputStream writer;

    private ConnectionController() {
        try {
            this.socket = new Socket(ConnectionConfig.HOST, ConnectionConfig.PORT);
            this.writer = new ObjectOutputStream(socket.getOutputStream());
            this.reader = new ObjectInputStream(socket.getInputStream());
            new ParallelThread<>(this::receiveMessages, this::routeMessages);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ConnectionController getConnectionController() {
        if (connectionController == null) {
            connectionController = new ConnectionController();
            loginConnectionController = new LoginConnectionController(connectionController);
            chatConnectionController = new ChatConnectionController(connectionController);
            loginConnectionController.prepareAndSendServerHandshakeMessage(null, Rsa.getPublicKey());
        }
        return connectionController;
    }

    private void receiveMessages(SynchronizedCollection<byte[]> messageBuffer) {
        while (this.socket.isConnected() && !this.socket.isClosed()) {
            try {
                byte[] message = (byte[]) this.reader.readObject();
                messageBuffer.put(message);
            } catch (IOException | ClassNotFoundException e) {
                closeSession();
                SceneBean.getScene().switchCollection("noConnection");
            }
        }
    }

    private void routeMessages(byte[] encryptedMessage) {
        try {
            byte[] decryptedMessage = decryptMessage(null, encryptedMessage, ECB);
            Message message = Serializer.deserialize(decryptedMessage);
            byte[] byteMessageContent = message.getContent();
            byte[] byteMessageType = message.getMessageType();
            if (message.getSenderId() != null) {
                byteMessageContent = decryptMessage(message.getSenderId(), message.getContent(), message.getCipherType());
                byteMessageType = decryptMessage(message.getSenderId(), message.getMessageType(), message.getCipherType());
            }
            MessageType messageType = Serializer.deserialize(byteMessageType);
            if (messageType.isAuthorizedConnection()) {
                chatConnectionController.routeMessage(messageType, byteMessageContent);
            } else {
                loginConnectionController.routeMessage(messageType, byteMessageContent);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(MessageType messageType, Object content, Long receiverId, EncryptionType encryptionType) {
        try {
            byte[] byteMessageType = Serializer.serialize(messageType);
            byte[] byteContent = Serializer.serialize(content);
            if (receiverId != null) {
                byteMessageType = encryptMessage(receiverId, byteMessageType, encryptionType);
                byteContent = encryptMessage(receiverId, byteContent, encryptionType);
            }
            Message message = Message.builder()
                    .receiverId(receiverId)
                    .senderId(receiverId != null ? getUserService().getUserId() : null)
                    .cipherType(receiverId == null ? ECB : getChatButtonController().getCipherType())
                    .messageType(byteMessageType)
                    .content(byteContent)
                    .build();
            byte[] byteMessage = Serializer.serialize(message);
            byte[] encryptedMessage = encryptMessage(null, byteMessage, (receiverId != null) ? AES : encryptionType);
            this.writer.writeObject(encryptedMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(MessageType messageType, Object content, Long receiverId) {
        sendMessage(messageType, content, receiverId, AES);
    }

    public void sendMessages(MessageType messageType, List<Object> contentList, Long receiverId, EncryptionType encryptionType) {
        try {
            List<byte[]> encryptedMessages = new ArrayList<>();
            for (Object content : contentList) {
                byte[] byteMessageType = Serializer.serialize(messageType);
                byte[] byteContent = Serializer.serialize(content);
                byte[] encryptedMessageType = encryptMessage(receiverId, byteMessageType, encryptionType);
                byte[] encryptedContent = encryptMessage(receiverId, byteContent, encryptionType);
                Message message = Message.builder()
                        .receiverId(receiverId)
                        .senderId(getUserService().getUserId())
                        .cipherType(receiverId == null ? ECB : getChatButtonController().getCipherType())
                        .messageType(encryptedMessageType)
                        .content(encryptedContent)
                        .build();
                byte[] byteMessage = Serializer.serialize(message);
                byte[] encryptedMessage = encryptMessage(null, byteMessage, (receiverId != null) ? AES : encryptionType);
                encryptedMessages.add(encryptedMessage);
            }
            new Thread(() -> sendMessagesInParallel(encryptedMessages, this.writer)).start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessages(MessageType messageType, List<Object> contentList, Long receiverId) {
        sendMessages(messageType, contentList, receiverId, AES);
    }

    public void sendMessage(MessageType messageType, Long receiverId) {
        sendMessage(messageType, new byte[0], receiverId, AES);
    }

    private byte[] decryptMessage(Long senderId, byte[] encryptedMessage, CipherType cipherType) {
        if (Aes.getSessionKeyBySessionPartnerId(senderId) != null) {
            return Aes.decrypt(senderId, encryptedMessage, (cipherType != null) ? cipherType : ECB);
        } else if (Rsa.getPublicKeyBySessionPartnerId(senderId) != null) {
            return Rsa.decryptMessage(encryptedMessage);
        } else {
            return encryptedMessage;
        }
    }

    private byte[] encryptMessage(Long receiverId, byte[] message, EncryptionType encryptionType) {
        if (encryptionType == AES) {
            CipherType cipherType = receiverId == null ? ECB : getChatButtonController().getCipherType();
            return Aes.encrypt(receiverId, message, cipherType);
        } else if (encryptionType == RSA) {
            Key publicKey = Rsa.getPublicKeyBySessionPartnerId(receiverId);
            return Rsa.encryptMessage(message, publicKey);
        } else {
            return message;
        }
    }

    private void sendMessagesInParallel(List<byte[]> messages, ObjectOutputStream writer) {
        try {
            for (byte[] message : messages) {
                writer.writeObject(message);
            }
        } catch (Exception e) {
            //silenced
        }
    }

    private void closeSession() {
        try {
            if (this.reader != null && this.socket != null && !this.socket.isClosed()) {
                this.reader.close();
            }
            if (this.writer != null && this.socket != null && !this.socket.isClosed()) {
                this.writer.close();
            }
            if (this.socket != null && !this.socket.isClosed()) {
                this.socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
