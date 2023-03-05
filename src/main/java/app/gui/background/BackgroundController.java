package app.gui.background;

public class BackgroundController {

    private static BackgroundController backgroundController;

    private final Background background;

    private BackgroundController() {
        this.background = new Background();
    }

    public static BackgroundController getBackgroundController() {
        if (backgroundController == null) {
            backgroundController = new BackgroundController();
        }
        return backgroundController;
    }

}
