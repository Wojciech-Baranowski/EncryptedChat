package common.encryption.rsaKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Serializer;
import common.encryption.Aes;
import lombok.Getter;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.file.Paths;

import static common.encryption.aesCipher.CipherType.ECB;
import static common.encryption.rsaKey.KeyConfig.*;
import static common.encryption.rsaKey.KeyType.PRIVATE;


public class Key implements Serializable {

    @JsonIgnore
    private transient final File file;
    @JsonIgnore
    private transient final ObjectMapper objectMapper;
    @Getter
    private BigInteger exponent;
    @Getter
    private BigInteger modulo;

    public Key() {
        this.file = null;
        this.objectMapper = null;
    }

    public Key(KeyType keyType, byte[] encryptionByteKey) {
        try {
            SecretKey encryptionKey = Aes.getKey(encryptionByteKey);
            this.file = Paths.get(keyType == PRIVATE ? PATH_TO_USER_PRIVATE_KEY : PATH_TO_USER_PUBLIC_KEY).toFile();
            this.objectMapper = new ObjectMapper();
            EncryptedKey encryptedKey = this.objectMapper.readValue(this.file, EncryptedKey.class);
            byte[] byteExponent = Aes.decrypt(encryptedKey.getExponent(), ECB, encryptionKey);
            byte[] byteModulo = Aes.decrypt(encryptedKey.getModulo(), ECB, encryptionKey);
            this.exponent = Serializer.deserialize(byteExponent);
            this.modulo = Serializer.deserialize(byteModulo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Key(BigInteger exponent, BigInteger modulo, KeyType keyType, byte[] encryptionByteKey) {
        SecretKey encryptionKey = Aes.getKey(encryptionByteKey);
        byte[] byteExponent = Serializer.serialize(exponent);
        byte[] byteModulo = Serializer.serialize(modulo);
        this.file = Paths.get(keyType == PRIVATE ? PATH_TO_USER_PRIVATE_KEY : PATH_TO_USER_PUBLIC_KEY).toFile();
        this.objectMapper = new ObjectMapper();
        EncryptedKey encryptedKey = EncryptedKey.builder()
                .exponent(Aes.encrypt(byteExponent, ECB, encryptionKey))
                .modulo(Aes.encrypt(byteModulo, ECB, encryptionKey))
                .build();
        if (!this.file.exists()) {
            createDirectories(keyType);
        }
        saveKey(encryptedKey);
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

    private void saveKey(EncryptedKey encryptedKey) {
        try {
            this.objectMapper.writeValue(this.file, encryptedKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
