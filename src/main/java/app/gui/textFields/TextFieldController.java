package app.gui.textFields;

import static app.gui.background.BackgroundController.getBackgroundController;

public class TextFieldController {

    private static TextFieldController textFieldController;

    private Message message;

    private TextFieldController() {
        this.message = new Message(getBackgroundController().getFileToSendBodyBackground());
    }

    public static TextFieldController getTextFieldController() {
        if (textFieldController == null) {
            textFieldController = new TextFieldController();
        }
        return textFieldController;
    }

}
