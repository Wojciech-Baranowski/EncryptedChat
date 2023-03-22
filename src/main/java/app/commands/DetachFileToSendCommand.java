package app.commands;

import app.engine.common.Command;

import static app.controllers.FileController.getFileController;

public class DetachFileToSendCommand implements Command {

    @Override
    public void execute() {
        getFileController().detachFile();
    }

}
