package common.message;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Message {

    private Long receiverId;
    private byte[] cipherType;
    private byte[] messageType;
    private byte[] content;

}
