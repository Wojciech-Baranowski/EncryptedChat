package server.userDataBase;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDataBaseRecord {

    private Long id;
    private String userName;
    private String password;

}
