package app.gui.login.texts;

import app.gui.login.backgrounds.BackgroundController;

public class TextController {

    private static TextController textController;

    private final Login login;
    private final Register register;
    private final Info info;

    private TextController() {
        this.login = new Login(BackgroundController.getBackgroundController().getLoginBackground());
        this.register = new Register(BackgroundController.getBackgroundController().getRegisterBackground());
        this.info = new Info(BackgroundController.getBackgroundController().getInfoBackground());
    }

    public static TextController getTextController() {
        if (textController == null) {
            textController = new TextController();
        }
        return textController;
    }

    public void setRegistrationSuccess() {
        this.info.setRegistrationSuccess();
    }

    public void setUserNameIsEmptyError() {
        this.info.setUserNameDoesNotExistError();
    }

    public void setUserNameDoesNotExistError() {
        this.info.setUserNameDoesNotExistError();
    }

    public void setUserNameAlreadyExistsError() {
        this.info.setUserNameAlreadyExistsError();
    }

    public void setIncorrectPasswordError() {
        this.info.setIncorrectPasswordError();
    }

}
