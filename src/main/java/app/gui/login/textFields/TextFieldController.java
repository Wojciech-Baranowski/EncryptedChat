package app.gui.login.textFields;

import static app.gui.login.backgrounds.BackgroundController.getBackgroundController;

public class TextFieldController {

    private static TextFieldController textFieldController;

    private final Login login;
    private final Register register;

    private TextFieldController() {
        this.login = new Login(getBackgroundController().getLoginBackground());
        this.register = new Register(getBackgroundController().getRegisterBackground());
    }

    public static TextFieldController getTextFieldController() {
        if (textFieldController == null) {
            textFieldController = new TextFieldController();
        }
        return textFieldController;
    }

    public String getLoginUserName() {
        return this.login.getUserName();
    }

    public String getLoginPassword() {
        return this.login.getPassword();
    }

    public String getRegisterUserName() {
        return this.register.getUserName();
    }

    public String getRegisterPassword() {
        return this.register.getPassword();
    }

    public String getRegisterRepeatedPassword() {
        return this.register.getRepeatedPassword();
    }

}
