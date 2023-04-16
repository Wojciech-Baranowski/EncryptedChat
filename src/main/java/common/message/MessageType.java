package common.message;


import lombok.Getter;

import java.io.Serializable;

public enum MessageType implements Serializable {

    LOGIN_REQUEST(false),
    REGISTRATION_REQUEST(false),
    AUTHORIZATION(false),
    SERVER_HANDSHAKE(false),
    SERVER_SESSION(false),
    FILE(true),
    TEXT(true),
    CONFIRMATION(true),
    USER_CONNECTION(true),
    ALL_USER_CONNECTION_REQUEST(true),
    USER_DISCONNECTION(true),
    CLIENT_HANDSHAKE(true);

    @Getter
    private final boolean authorizedConnection;

    MessageType(boolean authorizedConnection) {
        this.authorizedConnection = authorizedConnection;
    }

}
