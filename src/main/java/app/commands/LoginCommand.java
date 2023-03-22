package app.commands;

import app.engine.common.Command;

import static app.services.UserService.getUserService;

public class LoginCommand implements Command {

    @Override
    public void execute() {
        getUserService().loginUserRequest();
    }

}
