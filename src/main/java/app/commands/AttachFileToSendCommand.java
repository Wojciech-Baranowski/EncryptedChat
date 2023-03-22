package app.commands;

import app.engine.common.Command;

import static app.controllers.FileController.getFileController;

public class AttachFileToSendCommand implements Command {

    @Override
    public void execute() {
        getFileController().attachFile();
    }

}
