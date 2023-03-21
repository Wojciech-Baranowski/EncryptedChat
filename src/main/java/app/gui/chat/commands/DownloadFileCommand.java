package app.gui.chat.commands;

import app.engine.common.Command;

import static app.engine.input.InputBean.getInput;
import static app.files.FileController.getFileController;

public class DownloadFileCommand implements Command {

    private final int fileHashCode;

    public DownloadFileCommand(int fileHashCode) {
        this.fileHashCode = fileHashCode;
    }

    @Override
    public void execute() {
        getFileController().downloadReceivedFile(fileHashCode);
        getInput().resetMouseListener();
        getInput().resetKeyboardListener();
    }

}
