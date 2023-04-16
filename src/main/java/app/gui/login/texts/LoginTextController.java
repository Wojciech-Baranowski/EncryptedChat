package app.gui.login.texts;

import static app.gui.login.backgrounds.LoginBackgroundController.getLoginBackgroundController;

public class LoginTextController {

    private static LoginTextController loginTextController;

    private final LoginText loginText;
    private final RegisterText registerText;
    private final InfoText infoText;
    private final KeyPasswordText keyPasswordText;

    private LoginTextController() {
        this.loginText = new LoginText(getLoginBackgroundController().getLoginBackground());
        this.registerText = new RegisterText(getLoginBackgroundController().getRegisterBackground());
        this.infoText = new InfoText(getLoginBackgroundController().getInfoBackground());
        this.keyPasswordText = new KeyPasswordText(getLoginBackgroundController().getBackground());
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
        this.infoText.setUserNameIsEmptyError();
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

    public void setIncorrectKeyPasswordError() {
        this.infoText.setIncorrectKeyPasswordError();
    }

}
