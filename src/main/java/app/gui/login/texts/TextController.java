package app.gui.login.texts;

import app.gui.login.backgrounds.BackgroundController;

public class TextController {

    private static TextController textController;

    private final LoginText loginText;
    private final RegisterText registerText;
    private final InfoText infoText;

    private TextController() {
        this.loginText = new LoginText(BackgroundController.getBackgroundController().getLoginBackground());
        this.registerText = new RegisterText(BackgroundController.getBackgroundController().getRegisterBackground());
        this.infoText = new InfoText(BackgroundController.getBackgroundController().getInfoBackground());
    }

    public static TextController getTextController() {
        if (textController == null) {
            textController = new TextController();
        }
        return textController;
    }

    public void setRegistrationSuccess() {
        this.infoText.setRegistrationSuccess();
    }

    public void setUserNameIsEmptyError() {
        this.infoText.setUserNameDoesNotExistError();
    }

    public void setUserNameDoesNotExistError() {
        this.infoText.setUserNameDoesNotExistError();
    }

    public void setUserNameAlreadyExistsError() {
        this.infoText.setUserNameAlreadyExistsError();
    }

    public void setIncorrectPasswordError() {
        this.infoText.setIncorrectPasswordError();
    }

}
