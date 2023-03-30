package server.userDataBase;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    private final List<UserDataBaseRecord> userDataBaseRecords;

    public UserDataBase() {
        try {
            this.dataBase = Paths.get(PATH_TO_USER_DATABASE).toFile();
            this.objectMapper = new ObjectMapper();
            this.userDataBaseRecords = new ArrayList<>(List.of(this.objectMapper.readValue(this.dataBase, UserDataBaseRecord[].class)));
            this.userIdSequence = new UserIdSequence(userDataBaseRecords.stream().map(UserDataBaseRecord::getId).max(Long::compare).orElse(1L));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRecord(String userName, String password) {
        try {
            UserDataBaseRecord userDataBaseRecord = new UserDataBaseRecord(this.userIdSequence.getNextId(), userName, password);
            this.userDataBaseRecords.add(userDataBaseRecord);
            this.objectMapper.writeValue(this.dataBase, this.userDataBaseRecords);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDataBaseRecord findUserDataBaseRecordByUserId(Long userId) {
        return this.userDataBaseRecords.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public UserDataBaseRecord findUserDataBaseRecordByUserName(String userName) {
        return this.userDataBaseRecords.stream()
                .filter(user -> user.getUserName().equals(userName))
                .findFirst()
                .orElse(null);
    }

}
