package common.message;

import app.encryption.rsaKey.Key;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class HandshakeMessage {

    private Long senderId;
    private Key publicKey;

}
