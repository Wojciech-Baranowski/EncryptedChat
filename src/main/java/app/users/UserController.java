package app.users;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static app.gui.chat.buttons.ButtonController.getButtonController;
import static app.gui.chat.texts.TextController.getTextController;
import static common.ConnectionConfig.HOST;
import static common.ConnectionConfig.PORT;

public class UserController {

    private static UserController userController;

    private User user;
    private final List<Integer> receiverIds;
    private final Socket socket;
    private final ObjectInputStream reader;
    private final ObjectOutputStream writer;

    private UserController() {
        try {
            this.receiverIds = new ArrayList<>();
            this.socket = new Socket(HOST, PORT);
            this.writer = new ObjectOutputStream(socket.getOutputStream());
            this.reader = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getTextController().setUserName(this.user.getUserName());
    }

    public static UserController getReceiversController() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }

    private void addReceiver(int receiverId) {
        this.receiverIds.add(receiverId);
        getButtonController().addReceiver(receiverId);
    }

    private void removeReceiver(int receiverId) {
        getButtonController().removeReceiver(receiverId);
        this.receiverIds.remove(Integer.valueOf(receiverId));
    }

}
