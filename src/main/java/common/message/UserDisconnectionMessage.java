package common.message;

import common.transportObjects.UserData;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDisconnectionMessage implements Serializable {

    private UserData userData;

}
