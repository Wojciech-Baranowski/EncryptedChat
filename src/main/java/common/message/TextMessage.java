package common.message;

import common.CipherConfig;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TextMessage implements Serializable {

    private Long senderId;
    private CipherConfig.CipherType cipherType;
    private byte[] text;

}
