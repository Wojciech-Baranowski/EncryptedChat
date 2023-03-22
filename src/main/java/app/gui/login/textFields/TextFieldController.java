package app.gui.login.textFields;

import static app.gui.login.backgrounds.BackgroundController.getBackgroundController;

public class TextFieldController {

    private static TextFieldController textFieldController;

    private final LoginTextField loginTextField;
    private final RegisterTextField registerTextField;

    private TextFieldController() {
        this.loginTextField = new LoginTextField(getBackgroundController().getLoginBackground());
        this.registerTextField = new RegisterTextField(getBackgroundController().getRegisterBackground());
    }

    public static TextFieldController getTextFieldController() {
        if (textFieldController == null) {
            textFieldController = new TextFieldController();
        }
        return textFieldController;
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
