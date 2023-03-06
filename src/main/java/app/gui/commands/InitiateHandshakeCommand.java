package app.gui.commands;

import engine.common.Command;

public class InitiateHandshakeCommand implements Command {

    private final Integer userId;

    public InitiateHandshakeCommand(Integer userId) {
        this.userId = userId;
    }

    @Override
    public void execute() {
        if (userId != null) {
            System.out.println(userId);
        }
    }

}
