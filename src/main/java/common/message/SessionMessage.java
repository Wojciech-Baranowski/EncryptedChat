package common.message;

import common.encryption.aesCipher.InitialVector;
import lombok.*;

import javax.crypto.SecretKey;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SessionMessage implements Serializable {

    private Long senderId;
    private SecretKey sessionKey;
    private InitialVector initialVector;

}
