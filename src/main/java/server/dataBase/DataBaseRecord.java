package server.dataBase;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DataBaseRecord {

    private Long id;
    private String userName;
    private String password;

}
