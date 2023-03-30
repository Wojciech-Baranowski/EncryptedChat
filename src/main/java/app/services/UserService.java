package app.services;

import app.connection.ConnectionController;
import common.transportObjects.UserData;
import common.transportObjects.UserDataProcessResponseType;

import java.util.ArrayList;
import java.util.List;

import static app.engine.scene.SceneBean.getScene;
import static app.gui.chat.buttons.ChatButtonController.getChatButtonController;
import static app.gui.chat.texts.ChatTextController.getChatTextController;
import static app.gui.login.textFields.LoginTextFieldController.getLoginTextFieldController;
import static app.gui.login.texts.LoginTextController.getLoginTextController;

public class UserService {

    private static UserService userService;

    private UserData userData;
    private final List<Long> receiverIds;

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
        String userName = getLoginTextFieldController().getRegisterUserName();
        String password = getLoginTextFieldController().getRegisterPassword();
        String repeatedPassword = getLoginTextFieldController().getRegisterRepeatedPassword();
        if (isRegistrationUserDataValid(userName, password, repeatedPassword)) {
            //hash
            ConnectionController.getLoginConnectionController().prepareAndSendRegisterRequestMessage(userName, password);
        }
    }

    public void loginUserRequest() {
        String userName = getLoginTextFieldController().getLoginUserName();
        String password = getLoginTextFieldController().getLoginPassword();
        if (isLoginUserDataValid(userName)) {
            //hash
            ConnectionController.getLoginConnectionController().prepareAndSendLoginRequestMessage(userName, password);
        }
    }

    public void processResponse(UserDataProcessResponseType userDataProcessResponseType, UserData userData) {
        switch (userDataProcessResponseType) {
            case LOGIN_SUCCESS -> setUserAndAskForAllUserConnections(userData);
            case REGISTRATION_SUCCESS -> getLoginTextController().setRegistrationSuccess();
            case LOGIN_FAILED_USERNAME_DOES_NOT_EXIST -> getLoginTextController().setUserNameDoesNotExistError();
            case LOGIN_FAILED_INCORRECT_PASSWORD -> getLoginTextController().setIncorrectPasswordError();
            case REGISTRATION_FAILED_USERNAME_ALREADY_EXIST -> getLoginTextController().setUserNameAlreadyExistsError();
        }
    }

    public void addReceiver(Long receiverId, String receiverUserName) {
        this.receiverIds.add(receiverId);
        getChatButtonController().addReceiver(receiverId, receiverUserName);
    }

    public void removeReceiver(Long receiverId) {
        getChatButtonController().removeReceiver(receiverId);
        this.receiverIds.remove(receiverId);
    }

    private void setUserAndAskForAllUserConnections(UserData userData) {
        getScene().switchCollection("chat");
        this.userData = userData;
        getChatTextController().setUserName(this.userData.getUserName());
        ConnectionController.getChatConnectionController().prepareAndSendAllUserConnectionRequestMessage();
    }

    private boolean isRegistrationUserDataValid(String userName, String password, String repeatedPassword) {
        if (userName.equals("")) {
            getLoginTextController().setUserNameIsEmptyError();
            return false;
        }
        if (!password.equals(repeatedPassword)) {
            getLoginTextController().setGivenPasswordsDoNotMatchError();
            return false;
        }
        return true;
    }

    private boolean isLoginUserDataValid(String userName) {

        if (userName.equals("")) {
            getLoginTextController().setUserNameIsEmptyError();
            return false;
        }
        return true;
    }

}
