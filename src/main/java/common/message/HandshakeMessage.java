package common.message;

import common.encryption.rsaKey.Key;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class HandshakeMessage implements Serializable {

    private Long senderId;
    private Key publicKey;
    private boolean returning;

}
