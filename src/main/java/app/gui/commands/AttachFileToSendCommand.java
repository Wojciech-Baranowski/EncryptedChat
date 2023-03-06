package app.gui.commands;

import engine.common.Command;

public class AttachFileToSendCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Attached!");
    }

}
