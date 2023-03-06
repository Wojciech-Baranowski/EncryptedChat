package app.gui.commands;

import engine.common.Command;

public class DownloadFileCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Downloaded!");
    }

}
