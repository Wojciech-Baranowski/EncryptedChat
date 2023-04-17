package common.message;

import common.encryption.rsaKey.Key;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ServerHandshakeMessage implements Serializable {

    private Key publicKey;

}
