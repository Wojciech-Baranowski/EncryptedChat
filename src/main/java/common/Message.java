package common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Message {

    private Long senderId;
    private Long receiverId;
    private byte[] content;

}
