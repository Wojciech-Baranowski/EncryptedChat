package common.message;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Message {

    private Long receiverId;
    private CipherType cipherType;
    private byte[] messageType;
    private byte[] content;

}
