package app.commands;

import app.engine.common.Command;

import static app.services.FileService.getFileService;

public class AttachFileToSendCommand implements Command {

    @Override
    public void execute() {
        getFileService().attachFile();
    }

}
