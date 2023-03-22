package app.services;

import common.transportObjects.UserData;

import java.util.ArrayList;
import java.util.List;

import static app.engine.scene.SceneBean.getScene;
import static app.gui.chat.buttons.ButtonController.getButtonController;
import static app.gui.chat.texts.TextController.getTextController;

public class UserService {

    private static UserService userService;

    private UserData userData;
    private final List<Integer> receiverIds;

    private UserService() {
        this.receiverIds = new ArrayList<>();
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public void registerUserRequest() {

    }

    public void loginUserRequest() {

    }

    public void setUser(Long userId, String userName) {
        getScene().switchCollection("chat");
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
