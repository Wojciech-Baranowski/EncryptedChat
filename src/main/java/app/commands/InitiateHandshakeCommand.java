package app.commands;

import app.connection.ConnectionController;
import app.encryption.Rsa;
import app.encryption.rsaKey.Key;
import app.engine.common.Command;

import static app.services.UserService.getUserService;

public class InitiateHandshakeCommand implements Command {

    private final Long receiverId;

    public InitiateHandshakeCommand(Long receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public void execute() {
        Long senderId = getUserService().getUserId();
        Key publicKey = Rsa.getPublicKey();
        ConnectionController.getChatConnectionController().prepareAndSendHandshakeMessage(senderId, publicKey, receiverId);
    }

}
