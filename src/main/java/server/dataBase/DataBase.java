package server.dataBase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    public static final String PATH_TO_DATABASE = "database.json";
    private final ObjectMapper objectMapper;
    private final File dataBase;
    private final IdSequence idSequence;
    @Getter
    private List<DataBaseRecord> dataBaseRecords;

    public DataBase() {
        try {
            this.dataBase = Paths.get(PATH_TO_DATABASE).toFile();
            this.objectMapper = new ObjectMapper();
            this.dataBaseRecords = new ArrayList<>(List.of(this.objectMapper.readValue(this.dataBase, DataBaseRecord[].class)));
            this.idSequence = new IdSequence(dataBaseRecords.stream().map(DataBaseRecord::getId).max(Long::compare).orElseThrow());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRecord(DataBaseRecord dataBaseRecord) {
        try {
            this.dataBaseRecords.add(dataBaseRecord.toBuilder()
                    .id(this.idSequence.getNextId())
                    .build());
            this.objectMapper.writeValue(this.dataBase, this.dataBaseRecords);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
