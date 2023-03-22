package app.commands;

import app.engine.common.Command;

import static app.controllers.FileController.getFileController;

public class DownloadFileCommand implements Command {

    private final int fileHashCode;

    public DownloadFileCommand(int fileHashCode) {
        this.fileHashCode = fileHashCode;
    }

    @Override
    public void execute() {
        getFileController().downloadReceivedFile(fileHashCode);
    }

}
