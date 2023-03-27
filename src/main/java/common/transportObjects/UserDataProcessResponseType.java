package common.transportObjects;

import java.io.Serializable;

public enum UserDataProcessResponseType implements Serializable {

    LOGIN_SUCCESS,
    REGISTRATION_SUCCESS,
    LOGIN_FAILED_USERNAME_DOES_NOT_EXIST,
    LOGIN_FAILED_INCORRECT_PASSWORD,
    REGISTRATION_FAILED_USERNAME_ALREADY_EXIST,

}
