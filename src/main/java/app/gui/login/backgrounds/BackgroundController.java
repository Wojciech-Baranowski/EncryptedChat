package app.gui.login.backgrounds;

import app.engine.display.Drawable;

public class BackgroundController {

    private static BackgroundController backgroundController;

    private final Background background;
    private final LoginBackground loginBackground;
    private final RegisterBackground registerBackground;
    private final InfoBackground infoBackground;

    private BackgroundController() {
        this.background = new Background();
        this.loginBackground = new LoginBackground(getBackground());
        this.registerBackground = new RegisterBackground(getBackground());
        this.infoBackground = new InfoBackground(getBackground());
    }

    public static BackgroundController getBackgroundController() {
        if (backgroundController == null) {
            backgroundController = new BackgroundController();
        }
        return backgroundController;
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
