package common.message;


import lombok.Getter;

public enum MessageType {

    LOGIN(false),
    REGISTRATION(false),
    AUTHORIZATION(false),
    FILE(true),
    TEXT(true),
    CONFIRMATION(true),
    CONNECTION(true),
    DISCONNECTION(true);

    @Getter
    private final boolean authorizedConnection;

    MessageType(boolean authorizedConnection) {
        this.authorizedConnection = authorizedConnection;
    }

}
