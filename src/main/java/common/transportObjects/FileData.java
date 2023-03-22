package common.transportObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
@EqualsAndHashCode
public class FileData {

    private final String extension;
    private final String name;
    private final Long size;
    private final byte[] binaryFile;

    public FileData(File file) {
        try {
            int lastIndexOfDot = file.getAbsolutePath().lastIndexOf('.');
            int lastIndexOfSlash = file.getAbsolutePath().lastIndexOf('/');
            this.extension = file.getAbsolutePath().substring(lastIndexOfDot + 1);
            this.name = file.getAbsolutePath().substring(lastIndexOfSlash + 1, lastIndexOfDot);
            this.size = file.length();
            synchronized (this) {
                this.binaryFile = Files.readAllBytes(Path.of(file.toURI()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
