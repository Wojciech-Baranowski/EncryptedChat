package common.message;

import common.transportObjects.UserData;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserConnectionMessage implements Serializable {

    private UserData userData;

}
