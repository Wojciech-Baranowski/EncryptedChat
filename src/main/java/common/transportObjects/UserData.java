package common.transportObjects;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserData implements Serializable {

    private Long id;
    private String userName;

}
