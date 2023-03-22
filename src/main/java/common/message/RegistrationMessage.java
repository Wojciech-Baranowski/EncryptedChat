package common.message;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RegistrationMessage {

    private String userName;
    private String password;
    private String repeatedPassword;

}
