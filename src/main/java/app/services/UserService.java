package app.services;

import app.connection.ConnectionController;
import common.encryption.Sha256;
import common.transportObjects.UserData;
import common.transportObjects.UserDataProcessResponseType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static app.engine.scene.SceneBean.getScene;
import static app.gui.chat.buttons.ChatButtonController.getChatButtonController;
import static app.gui.chat.texts.ChatTextController.getChatTextController;
import static app.gui.login.textFields.LoginTextFieldController.getLoginTextFieldController;
import static app.gui.login.texts.LoginTextController.getLoginTextController;
import static app.services.FileService.getFileService;

public class UserService {

    private static UserService userService;

    private UserData userData;
    private final List<UserData> receiversData;

    private UserService() {
        this.receiversData = new ArrayList<>();
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
            String passwordHash = Arrays.toString(Sha256.hash(password));
            ConnectionController.getLoginConnectionController().prepareAndSendRegisterRequestMessage(userName, passwordHash);
        }
    }

    public void loginUserRequest() {
        String userName = getLoginTextFieldController().getLoginUserName();
        String password = getLoginTextFieldController().getLoginPassword();
        if (isLoginUserDataValid(userName)) {
            String passwordHash = Arrays.toString(Sha256.hash(password));
            ConnectionController.getLoginConnectionController().prepareAndSendLoginRequestMessage(userName, passwordHash);
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

    public void addReceiver(UserData userData) {
        this.receiversData.add(userData);
        getChatButtonController().addReceiver(userData);
    }

    public void removeReceiver(Long receiverId) {
        if (Objects.equals(getChatButtonController().getSelectedReceiverId(), receiverId) && getFileService().getNumberOfConfirmationsToReceive() > 0) {
            getChatTextController().setCurrentUploadInfoAsError();
        }
        getChatButtonController().removeReceiver(receiverId);
        this.receiversData.remove(receiverId);
    }

    public Long getUserId() {
        return this.userData.getId();
    }

    public UserData getReceiverUserDataById(Long receiverId) {
        return this.receiversData.stream().
                filter(data -> data.getId().equals(receiverId))
                .findAny()
                .orElse(null);
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
