package common.message;

import app.encryption.aesCipher.CipherType;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Message implements Serializable {

    private Long receiverId;
    private Long senderId;
    private CipherType cipherType;
    private byte[] messageType;
    private byte[] content;

}
