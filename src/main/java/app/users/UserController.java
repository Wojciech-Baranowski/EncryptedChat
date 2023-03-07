package app.users;

import java.util.ArrayList;
import java.util.List;

import static app.gui.buttons.ButtonController.getButtonController;
import static app.gui.texts.TextController.getTextController;

public class UserController {

    private static UserController userController;

    private User user;
    private final List<User> receivers;

    private UserController() {
        this.receivers = new ArrayList<>();
        addReceiver(1);
    }

    public static UserController getReceiversController() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }

    public void setUser(int newUserId) {
        this.user = new User(newUserId);
        getTextController().setUserName(this.user.getId());
    }

    public void addReceiver(int receiverId) {
        this.receivers.add(new User(receiverId));
        getButtonController().addReceiver(receiverId);
    }

    public void removeReceiver(int receiverId) {
        getButtonController().removeReceiver(receiverId);
        this.receivers.remove(this.receivers.stream()
                .filter(user -> user.getId() == receiverId)
                .findFirst()
                .orElse(null));
    }

}
