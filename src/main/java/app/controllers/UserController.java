package app.controllers;

import common.transportObjects.UserData;

import java.util.ArrayList;
import java.util.List;

import static app.gui.chat.buttons.ButtonController.getButtonController;
import static app.gui.chat.texts.TextController.getTextController;

public class UserController {

    private static UserController userController;

    private UserData userData;
    private final List<Integer> receiverIds;

    private UserController() {
        this.receiverIds = new ArrayList<>();
    }

    public static UserController getUserController() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }

    public void registerUserRequest() {

    }

    public void loginUserRequest() {

    }

    public void setUser(Long userId, String userName) {
        this.userData = UserData.builder()
                .id(userId)
                .userName(userName)
                .build();
        getTextController().setUserName(this.userData.getUserName());
    }

    public void addReceiver(int receiverId) {
        this.receiverIds.add(receiverId);
        getButtonController().addReceiver(receiverId);
    }

    public void removeReceiver(int receiverId) {
        getButtonController().removeReceiver(receiverId);
        this.receiverIds.remove(Integer.valueOf(receiverId));
    }

}
