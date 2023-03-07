package app.gui.commands;

import engine.common.Command;

import static app.files.FileController.getFileController;
import static engine.input.InputBean.getInput;

public class AttachFileToSendCommand implements Command {

    @Override
    public void execute() {
        getFileController().attachFile();
        getInput().resetMouseListener();
        getInput().resetKeyboardListener();
    }

}
