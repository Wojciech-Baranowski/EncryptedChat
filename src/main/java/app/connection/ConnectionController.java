package app.connection;

import app.engine.listener.ParallelThread;
import app.engine.listener.SynchronizedCollection;
import common.CipherConfig;
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

    private void receiveMessages(SynchronizedCollection<Message> messageBuffer) {
        System.out.println("Test");
        while (socket.isConnected()) {
            try {
                Message message = (Message) this.reader.readObject();
                messageBuffer.put(message);
            } catch (IOException | ClassNotFoundException e) {
                closeSession();
                throw new RuntimeException(e);
            }
        }
    }

    private void routeMessages(Message message) {
        //decrypt
        byte[] decryptedMessageType = message.getMessageType();
        byte[] decryptedCipherType = message.getCipherType();
        byte[] decryptedContent = message.getContent();
        MessageType messageType = Serializer.deserialize(decryptedMessageType);
        CipherConfig.CipherType cipherType = Serializer.deserialize(decryptedCipherType);
        if (messageType.isAuthorizedConnection()) {
            chatConnectionController.routeMessage(messageType, cipherType, decryptedContent);
        } else {
            loginConnectionController.routeMessage(messageType, decryptedContent);
        }
    }

    public void sendMessage(MessageType messageType, Object content, Long receiverId) {
        try {
            //encrypt
            byte[] encryptedMessageType = Serializer.serialize(messageType);
            byte[] encryptedCipherType = Serializer.serialize(getChatButtonController().getCipherType());
            byte[] encryptedContent = Serializer.serialize(content);
            Message message = Message.builder()
                    .receiverId(receiverId)
                    .messageType(encryptedMessageType)
                    .cipherType(encryptedCipherType)
                    .content(encryptedContent)
                    .build();
            this.writer.writeObject(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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