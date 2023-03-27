package app.gui.login.texts;

import app.gui.login.backgrounds.LoginBackgroundController;

public class LoginTextController {

    private static LoginTextController loginTextController;

    private final LoginText loginText;
    private final RegisterText registerText;
    private final InfoText infoText;

    private LoginTextController() {
        this.loginText = new LoginText(LoginBackgroundController.getLoginBackgroundController().getLoginBackground());
        this.registerText = new RegisterText(LoginBackgroundController.getLoginBackgroundController().getRegisterBackground());
        this.infoText = new InfoText(LoginBackgroundController.getLoginBackgroundController().getInfoBackground());
    }

    public static LoginTextController getLoginTextController() {
        if (loginTextController == null) {
            loginTextController = new LoginTextController();
        }
        return loginTextController;
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

    public void setGivenPasswordsDoNotMatchError() {
        this.infoText.setGivenPasswordsDoNotMatchError();
    }

}
