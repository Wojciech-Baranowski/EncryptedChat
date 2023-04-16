package common.message;

import lombok.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SessionMessage {

    private Long senderId;
    private SecretKey sessionKey;
    private IvParameterSpec initialVector;
    private int jShit;

}
