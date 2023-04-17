package app.commands;

import app.connection.ConnectionController;
import app.engine.common.Command;
import common.encryption.Aes;
import common.encryption.Rsa;
import common.encryption.rsaKey.Key;

import static app.services.UserService.getUserService;

public class InitiateHandshakeCommand implements Command {

    private final Long receiverId;

    public InitiateHandshakeCommand(Long receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public void execute() {
        Long senderId = getUserService().getUserId();
        if (Aes.getSessionKeyBySessionPartnerId(receiverId) == null) {
            Key publicKey = Rsa.getPublicKey();
            ConnectionController.getChatConnectionController().prepareAndSendHandshakeMessage(senderId, publicKey, receiverId, false);
        }
    }

}
