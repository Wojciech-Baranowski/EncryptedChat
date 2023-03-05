package app.gui.buttons;

public class ButtonController {

    private static ButtonController buttonController;

    private ButtonController() {

    }

    public static ButtonController getButtonController() {
        if (buttonController == null) {
            buttonController = new ButtonController();
        }
        return buttonController;
    }

}
