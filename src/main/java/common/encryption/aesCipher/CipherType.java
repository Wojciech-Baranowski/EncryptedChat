package common.encryption.aesCipher;

import lombok.Getter;

import java.io.Serializable;

public enum CipherType implements Serializable {

    CBC("AES/CBC/PKCS5Padding"),
    ECB("AES/ECB/PKCS5Padding");

    @Getter
    private final String cipherName;

    CipherType(String cipherName) {
        this.cipherName = cipherName;
    }
}
