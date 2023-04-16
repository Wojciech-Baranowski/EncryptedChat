package app.gui.login.backgrounds;

import app.engine.display.Drawable;

public class LoginBackgroundController {

    private static LoginBackgroundController loginBackgroundController;

    private final Background background;
    private final LoginBackground loginBackground;
    private final RegisterBackground registerBackground;
    private final InfoBackground infoBackground;
    private final KeyPasswordBackground keyPasswordBackground;

    private LoginBackgroundController() {
        this.background = new Background();
        this.loginBackground = new LoginBackground(getBackground());
        this.registerBackground = new RegisterBackground(getBackground());
        this.infoBackground = new InfoBackground(getBackground());
        this.keyPasswordBackground = new KeyPasswordBackground(getBackground());
    }

    public static LoginBackgroundController getLoginBackgroundController() {
        if (loginBackgroundController == null) {
            loginBackgroundController = new LoginBackgroundController();
        }
        return loginBackgroundController;
    }

    public Drawable getBackground() {
        return this.background.getBackground();
    }

    public Drawable getLoginBackground() {
        return this.loginBackground.getLoginBackground();
    }

    public Drawable getRegisterBackground() {
        return this.registerBackground.getRegisterBackground();
    }

    public Drawable getInfoBackground() {
        return this.infoBackground.getInfoBackground();
    }

}
