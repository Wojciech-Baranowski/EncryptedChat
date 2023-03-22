package app.commands;

import app.engine.common.Command;

public class InitiateHandshakeCommand implements Command {

    private final Long userId;

    public InitiateHandshakeCommand(Long userId) {
        this.userId = userId;
    }

    @Override
    public void execute() {

    }

}
