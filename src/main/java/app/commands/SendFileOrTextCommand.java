package app.commands;

import app.engine.common.Command;

import static app.services.FileService.getFileService;

public class SendFileOrTextCommand implements Command {

    @Override
    public void execute() {
        getFileService().sendFileOrText();
    }

}
