package server.userDataBase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static server.userDataBase.UserDatabaseConfig.PATH_TO_USER_DATABASE;

public class UserDataBase {

    private final ObjectMapper objectMapper;
    private final File dataBase;
    private final UserIdSequence userIdSequence;
    @Getter
    private List<UserDataBaseRecord> userDataBaseRecords;

    public UserDataBase() {
        try {
            this.dataBase = Paths.get(PATH_TO_USER_DATABASE).toFile();
            this.objectMapper = new ObjectMapper();
            this.userDataBaseRecords = new ArrayList<>(List.of(this.objectMapper.readValue(this.dataBase, UserDataBaseRecord[].class)));
            this.userIdSequence = new UserIdSequence(userDataBaseRecords.stream().map(UserDataBaseRecord::getId).max(Long::compare).orElseThrow());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRecord(UserDataBaseRecord userDataBaseRecord) {
        try {
            this.userDataBaseRecords.add(userDataBaseRecord.toBuilder()
                    .id(this.userIdSequence.getNextId())
                    .build());
            this.objectMapper.writeValue(this.dataBase, this.userDataBaseRecords);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}