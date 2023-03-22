package app.commands;

import app.engine.common.Command;

import static app.controllers.UserController.getUserController;

public class LoginCommand implements Command {

    @Override
    public void execute() {
        getUserController().loginUserRequest();
    }

}
