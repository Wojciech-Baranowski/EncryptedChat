package app.encryption.rsaKey;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EncryptedKey {

    private byte[] exponent;
    private byte[] modulo;

}
