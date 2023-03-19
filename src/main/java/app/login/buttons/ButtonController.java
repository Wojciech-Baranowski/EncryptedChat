package app.login.buttons;

import static app.login.backgrounds.BackgroundController.getBackgroundController;

public class ButtonController {

    private static ButtonController buttonController;

    private final Login login;
    private final Register register;

    private ButtonController() {
        this.login = new Login(getBackgroundController().getLoginBackground());
        this.register = new Register(getBackgroundController().getRegisterBackground());
    }

    public static ButtonController getButtonController() {
        if (buttonController == null) {
            buttonController = new ButtonController();
        }
        return buttonController;
    }

}
