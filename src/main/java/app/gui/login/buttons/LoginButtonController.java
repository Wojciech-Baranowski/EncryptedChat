package app.gui.login.buttons;

import static app.gui.login.backgrounds.LoginBackgroundController.getLoginBackgroundController;

public class LoginButtonController {

    private static LoginButtonController loginButtonController;

    private final LoginButton loginButton;
    private final RegisterButton registerButton;

    private LoginButtonController() {
        this.loginButton = new LoginButton(getLoginBackgroundController().getLoginBackground());
        this.registerButton = new RegisterButton(getLoginBackgroundController().getRegisterBackground());
    }

    public static LoginButtonController getLoginButtonController() {
        if (loginButtonController == null) {
            loginButtonController = new LoginButtonController();
        }
        return loginButtonController;
    }

}
