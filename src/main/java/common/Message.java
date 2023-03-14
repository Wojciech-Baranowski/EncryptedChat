package common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Message {

    private int senderId;
    private int receiverId;
    private byte[] content;

}
