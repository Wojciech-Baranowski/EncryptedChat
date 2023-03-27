package common.message;

import common.transportObjects.UserData;
import common.transportObjects.UserDataProcessResponseType;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AuthorizationMessage implements Serializable {

    private UserDataProcessResponseType userDataProcessResponseType;
    private UserData userData;

}
