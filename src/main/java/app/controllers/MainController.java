package app.controllers;

import common.CipherConfig;
import common.Serializer;
import common.message.Message;
import common.message.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static app.gui.chat.buttons.ButtonController.getButtonController;

public class MainController implements Runnable {

    private final ChatController chatController;
    private final LoginController loginController;
    private final Long clientId;
    private final Socket socket;
    private final ObjectInputStream reader;
    private final ObjectOutputStream writer;

    public MainController(Socket socket, Long clientId) {
        try {
            this.loginController = new LoginController(this);
            this.chatController = new ChatController(this);
            this.socket = socket;
            this.clientId = clientId;
            this.writer = new ObjectOutputStream(socket.getOutputStream());
            this.reader = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeSession();
        }
    }

    @Override
    public void run() {
        routeMessages();
    }

    private void routeMessages() {
        while (socket.isConnected()) {
            try {
                Message message = (Message) this.reader.readObject();
                //decrypt
                byte[] decryptedMessageType = message.getMessageType();
                byte[] decryptedCipherType = message.getCipherType();
                byte[] decryptedContent = message.getContent();
                MessageType messageType = Serializer.deserialize(decryptedMessageType);
                CipherConfig.CipherType cipherType = Serializer.deserialize(decryptedCipherType);
                if (messageType.isAuthorizedConnection()) {
                    this.chatController.routeMessage(messageType, cipherType, decryptedContent);
                } else {
                    this.loginController.routeMessage(messageType, decryptedContent);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                closeSession();
            }
        }
    }

    private void sendMessageToServer(MessageType messageType, Object content) throws IOException {
        byte[] binaryMessageType = Serializer.serialize(messageType);
        byte[] binaryCipherType = Serializer.serialize(getButtonController().getCipherType());
        byte[] binaryContent = Serializer.serialize(content);
        //encrypt
        byte[] encryptedMessageType = binaryMessageType;
        byte[] encryptedCipherType = binaryCipherType;
        byte[] encryptedContent = binaryContent;
        Message message = Message.builder()
                .receiverId(getButtonController().getSelectedReceiverId())
                .messageType(encryptedMessageType)
                .cipherType(encryptedCipherType)
                .content(encryptedContent)
                .build();
        this.writer.writeObject(message);
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
            e.printStackTrace();
        }
    }

}
