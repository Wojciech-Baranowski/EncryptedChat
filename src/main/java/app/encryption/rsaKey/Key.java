package app.encryption.rsaKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;

import static app.encryption.rsaKey.KeyConfig.*;
import static app.encryption.rsaKey.KeyType.PRIVATE;


public class Key {

    @JsonIgnore
    private final File file;
    @JsonIgnore
    private final ObjectMapper objectMapper;
    @Getter
    private final BigInteger exponent;
    @Getter
    private final BigInteger modulo;

    public Key() {
        this.file = null;
        this.objectMapper = null;
        this.exponent = null;
        this.modulo = null;
    }

    public Key(KeyType keyType) {
        try {
            this.file = Paths.get(keyType == PRIVATE ? PATH_TO_USER_PRIVATE_KEY : PATH_TO_USER_PUBLIC_KEY).toFile();
            this.objectMapper = new ObjectMapper();
            Key fileKey = this.objectMapper.readValue(this.file, Key.class);
            this.exponent = fileKey.getExponent();
            this.modulo = fileKey.getModulo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Key(BigInteger exponent, BigInteger modulo, KeyType keyType) {
        this.file = Paths.get(keyType == PRIVATE ? PATH_TO_USER_PRIVATE_KEY : PATH_TO_USER_PUBLIC_KEY).toFile();
        this.objectMapper = new ObjectMapper();
        this.exponent = exponent;
        this.modulo = modulo;
        if (!this.file.exists()) {
            createDirectories(keyType);
        }
        saveKey();
    }

    private void createDirectories(KeyType keyType) {
        try {
            File keysDirectory = Paths.get(PATH_TO_USER_KEYS_DIRECTORY).toFile();
            if (!keysDirectory.exists()) {
                keysDirectory.mkdir();
            }
            File keyDirectory = Paths.get(keyType == PRIVATE ? PATH_TO_USER_PRIVATE_KEY_DIRECTORY : PATH_TO_USER_PUBLIC_KEY_DIRECTORY).toFile();
            if (!keyDirectory.exists()) {
                keyDirectory.mkdir();
            }
            this.file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveKey() {
        try {
            this.objectMapper.writeValue(this.file, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
