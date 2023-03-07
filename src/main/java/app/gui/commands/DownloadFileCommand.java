package app.gui.commands;

import engine.common.Command;

import static app.files.FileController.getFileController;
import static engine.input.InputBean.getInput;

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
