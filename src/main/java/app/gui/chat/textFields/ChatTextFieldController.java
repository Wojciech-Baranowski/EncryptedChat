package app.gui.chat.textFields;

import app.engine.input.textField.TextField;

import static app.gui.chat.backgrounds.ChatBackgroundController.getChatBackgroundController;

public class ChatTextFieldController {

    private static ChatTextFieldController chatTextFieldController;

    private final MessageTextField messageTextField;

    private ChatTextFieldController() {
        this.messageTextField = new MessageTextField(getChatBackgroundController().getFileToSendBodyBackground());
    }

    public static ChatTextFieldController getChatTextFieldController() {
        if (chatTextFieldController == null) {
            chatTextFieldController = new ChatTextFieldController();
        }
        return chatTextFieldController;
    }

    public TextField getMessageTextField() {
        return this.messageTextField.getTextField();
    }

}
