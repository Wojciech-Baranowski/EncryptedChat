package app.gui.commands;

import app.files.FileController;
import engine.common.Command;

import static app.gui.buttons.ButtonController.getButtonController;

public class SendFileCommand implements Command {

    @Override
    public void execute() {
        if (getButtonController().getSelectedReceiverId() != null
                && getButtonController().getCipherType() != null
                && FileController.getFileController().getAttachedFile() != null) {
            System.out.println("sent!");
        }
    }

}
