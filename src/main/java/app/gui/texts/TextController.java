package app.gui.texts;

public class TextController {

    private static TextController textController;

    private TextController() {

    }

    public static TextController getTextController() {
        if (textController == null) {
            textController = new TextController();
        }
        return textController;
    }

}
