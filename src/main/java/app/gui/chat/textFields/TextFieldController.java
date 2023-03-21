package app.gui.chat.textFields;

import app.engine.input.textField.TextField;

import static app.gui.chat.backgrounds.BackgroundController.getBackgroundController;

public class TextFieldController {

    private static TextFieldController textFieldController;

    private final Message message;

    private TextFieldController() {
        this.message = new Message(getBackgroundController().getFileToSendBodyBackground());
    }

    public static TextFieldController getTextFieldController() {
        if (textFieldController == null) {
            textFieldController = new TextFieldController();
        }
        return textFieldController;
    }

    public TextField getMessageTextField() {
        return this.message.getTextField();
    }

}
