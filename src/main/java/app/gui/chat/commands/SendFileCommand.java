package app.gui.chat.commands;

import app.engine.common.Command;
import app.files.FileController;
import app.gui.chat.buttons.ButtonController;

public class SendFileCommand implements Command {

    @Override
    public void execute() {
        if (ButtonController.getButtonController().getSelectedReceiverId() != null
                && ButtonController.getButtonController().getCipherType() != null
                && FileController.getFileController().getAttachedFile() != null) {
            System.out.println("sent!");
        }
    }

}
