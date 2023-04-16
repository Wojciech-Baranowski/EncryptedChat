package app.gui.login.textFields;

import static app.gui.login.backgrounds.LoginBackgroundController.getLoginBackgroundController;

public class LoginTextFieldController {

    private static LoginTextFieldController loginTextFieldController;

    private final LoginTextField loginTextField;
    private final RegisterTextField registerTextField;

    private LoginTextFieldController() {
        this.loginTextField = new LoginTextField(getLoginBackgroundController().getLoginBackground());
        this.registerTextField = new RegisterTextField(getLoginBackgroundController().getRegisterBackground());
    }

    public static LoginTextFieldController getLoginTextFieldController() {
        if (loginTextFieldController == null) {
            loginTextFieldController = new LoginTextFieldController();
        }
        return loginTextFieldController;
    }

    public String getLoginUserName() {
        return this.loginTextField.getUserName();
    }

    public String getLoginPassword() {
        return this.loginTextField.getPassword();
    }

    public String getRegisterUserName() {
        return this.registerTextField.getUserName();
    }

    public String getRegisterPassword() {
        return this.registerTextField.getPassword();
    }

    public String getRegisterRepeatedPassword() {
        return this.registerTextField.getRepeatedPassword();
    }

}
