package common.message;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Message implements Serializable {

    private Long receiverId;
    private byte[] messageType;
    private byte[] content;

}
