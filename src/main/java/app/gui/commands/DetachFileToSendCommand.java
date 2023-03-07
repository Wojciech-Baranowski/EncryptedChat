package app.gui.commands;

import engine.common.Command;

import static app.files.FileController.getFileController;

public class DetachFileToSendCommand implements Command {

    @Override
    public void execute() {
        getFileController().detachFile();
    }

}
