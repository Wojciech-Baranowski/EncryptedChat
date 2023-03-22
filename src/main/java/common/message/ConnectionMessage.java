package common.message;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ConnectionMessage {

    private int userId;
    private String userName;

}
