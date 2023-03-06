package app.gui.commands;

import engine.common.Command;

public class SendFileCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Sent!");
    }

}
