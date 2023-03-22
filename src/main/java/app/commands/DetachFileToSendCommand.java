package app.commands;

import app.engine.common.Command;

import static app.services.FileService.getFileService;

public class DetachFileToSendCommand implements Command {

    @Override
    public void execute() {
        getFileService().detachFile();
    }

}
