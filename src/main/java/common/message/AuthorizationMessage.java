package common.message;

import common.transportObjects.UserData;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AuthorizationMessage {

    public enum UserDataResponseType {

        LOGIN_SUCCESS,
        REGISTRATION_SUCCESS,
        LOGIN_FAILED_USERNAME_DOES_NOT_EXIST,
        LOGIN_FAILED_INCORRECT_PASSWORD,
        REGISTRATION_FAILED_USERNAME_ALREADY_EXIST,
        REGISTRATION_FAILED_USERNAME_IS_EMPTY

    }

    private UserDataResponseType userDataResponseType;
    private UserData userData;

}
