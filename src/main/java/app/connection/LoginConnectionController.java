package app.connection;

import common.Serializer;
import common.encryption.Aes;
import common.encryption.Rsa;
import common.encryption.aesCipher.InitialVector;
import common.encryption.rsaKey.Key;
import common.message.*;

import javax.crypto.SecretKey;

import static app.services.UserService.getUserService;
import static common.EncryptionType.NONE;
import static common.EncryptionType.RSA;
import static common.message.MessageType.*;

public class LoginConnectionController {

    private final ConnectionController connectionController;

    public LoginConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    public void routeMessage(MessageType messageType, byte[] content) {
        switch (messageType) {
            case AUTHORIZATION -> processAuthorizationMessage(content);
            case SERVER_HANDSHAKE -> processServerHandshakeMessage(content);
        }
    }

    public void prepareAndSendRegisterRequestMessage(String userName, String passwordHash) {
        RegistrationMessage registrationMessage = new RegistrationMessage(userName, passwordHash);
        this.connectionController.sendMessage(REGISTRATION_REQUEST, registrationMessage, null);
    }

    public void prepareAndSendLoginRequestMessage(String userName, String passwordHash) {
        LoginMessage loginMessage = new LoginMessage(userName, passwordHash);
        this.connectionController.sendMessage(LOGIN_REQUEST, loginMessage, null);
    }

    public void prepareAndSendServerHandshakeMessage(Long receiverId, Key publicKey) {
        ServerHandshakeMessage handshakeMessage = new ServerHandshakeMessage(publicKey);
        this.connectionController.sendMessage(SERVER_HANDSHAKE, handshakeMessage, receiverId, NONE);
    }

    public void prepareAndSendSessionMessage(Long receiverId) {
        Aes.sessionInitialize(receiverId);
        SecretKey sessionKey = Aes.getSessionKeyBySessionPartnerId(receiverId);
        InitialVector initialVector = Aes.getInitialVectorBySessionPartnerId(receiverId);
        ServerSessionMessage sessionMessage = new ServerSessionMessage(sessionKey, initialVector);
        this.connectionController.sendMessage(SERVER_SESSION, sessionMessage, receiverId, RSA);
    }

    private void processAuthorizationMessage(byte[] content) {
        try {
            AuthorizationMessage authorizationMessage = Serializer.deserialize(content);
            getUserService().processResponse(authorizationMessage.getUserDataProcessResponseType(), authorizationMessage.getUserData());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processServerHandshakeMessage(byte[] content) {
        ServerHandshakeMessage serverHandshakeMessage = Serializer.deserialize(content);
        Rsa.addPublicKeyBySessionPartnerId(null, serverHandshakeMessage.getPublicKey());
        prepareAndSendSessionMessage(null);
    }

}
