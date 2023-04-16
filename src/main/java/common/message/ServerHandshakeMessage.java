package common.message;

import app.encryption.rsaKey.Key;
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
