package app.connection;

import app.engine.listener.ParallelThread;
import app.engine.listener.SynchronizedCollection;
import app.engine.scene.SceneBean;
import common.ConnectionConfig;
import common.Serializer;
import common.message.Message;
import common.message.MessageType;
import lombok.Getter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static app.gui.chat.buttons.ChatButtonController.getChatButtonController;
import static common.CipherConfig.CipherType.EBC;

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
        }
        return connectionController;
    }

    private void receiveMessages(SynchronizedCollection<byte[]> messageBuffer) {
        while (this.socket.isConnected()) {
            try {
                byte[] message = (byte[]) this.reader.readObject();
                messageBuffer.put(message);
            } catch (IOException | ClassNotFoundException e) {
                closeSession();
                SceneBean.getScene().switchCollection("noConnection");
            }
        }
    }

    private void routeMessages(byte[] message) {
        try {
            //decryptServ
            Message decryptedMessage = Serializer.deserialize(message);
            //decrypt2
            byte[] decryptedMessageType = decryptedMessage.getMessageType();
            byte[] decryptedContent = decryptedMessage.getContent();
            MessageType messageType = Serializer.deserialize(decryptedMessageType);
            if (messageType.isAuthorizedConnection()) {
                chatConnectionController.routeMessage(messageType, decryptedContent);
            } else {
                loginConnectionController.routeMessage(messageType, decryptedContent);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(MessageType messageType, Object content, Long receiverId) {
        try {
            //encrypt
            byte[] encryptedMessageType = Serializer.serialize(messageType);
            byte[] encryptedContent = Serializer.serialize(content);
            Message message = Message.builder()
                    .receiverId(receiverId)
                    .cipherType(receiverId == null ? EBC : getChatButtonController().getCipherType())
                    .messageType(encryptedMessageType)
                    .content(encryptedContent)
                    .build();
            //encryptServ
            byte[] encryptedMessage = Serializer.serialize(message);
            this.writer.writeObject(encryptedMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(MessageType messageType, Long receiverId) {
        sendMessage(messageType, new byte[0], receiverId);
    }

    private void closeSession() {
        try {
            if (this.reader != null)
                this.reader.close();
            if (this.writer != null)
                this.writer.close();
            if (this.socket != null)
                this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
