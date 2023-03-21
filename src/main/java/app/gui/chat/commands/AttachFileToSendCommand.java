package app.gui.chat.commands;

import app.engine.common.Command;

import static app.engine.input.InputBean.getInput;
import static app.files.FileController.getFileController;

public class AttachFileToSendCommand implements Command {

    @Override
    public void execute() {
        getFileController().attachFile();
        getInput().resetMouseListener();
        getInput().resetKeyboardListener();
    }

}
