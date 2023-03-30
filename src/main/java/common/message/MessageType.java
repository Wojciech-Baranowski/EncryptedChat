package common.message;


import lombok.Getter;

import java.io.Serializable;

public enum MessageType implements Serializable {

    LOGIN_REQUEST(false),
    REGISTRATION_REQUEST(false),
    AUTHORIZATION(false),
    FILE(true),
    TEXT(true),
    CONFIRMATION(true),
    USER_CONNECTION(true),
    ALL_USER_CONNECTION_REQUEST(true),
    USER_DISCONNECTION(true);

    @Getter
    private final boolean authorizedConnection;

    MessageType(boolean authorizedConnection) {
        this.authorizedConnection = authorizedConnection;
    }

}
