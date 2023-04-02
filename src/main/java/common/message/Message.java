package common.message;

import common.CipherConfig;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Message implements Serializable {

    private Long receiverId;
    private CipherConfig.CipherType cipherType;
    private byte[] messageType;
    private byte[] content;

}
