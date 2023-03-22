package app.gui.login.buttons;

import static app.gui.login.backgrounds.BackgroundController.getBackgroundController;

public class ButtonController {

    private static ButtonController buttonController;

    private final LoginButton loginButton;
    private final RegisterButton registerButton;

    private ButtonController() {
        this.loginButton = new LoginButton(getBackgroundController().getLoginBackground());
        this.registerButton = new RegisterButton(getBackgroundController().getRegisterBackground());
    }

    public static ButtonController getButtonController() {
        if (buttonController == null) {
            buttonController = new ButtonController();
        }
        return buttonController;
    }

}
