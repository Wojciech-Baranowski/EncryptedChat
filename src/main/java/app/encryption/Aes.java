package app.encryption;

import app.encryption.aesCipher.CipherType;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import static app.encryption.aesCipher.CipherType.ECB;

public class Aes {

    private static final int KEY_SIZE = 256;
    public static final Map<Long, SecretKey> sessionKeyMap = new HashMap<>();
    public static final Map<Long, IvParameterSpec> initialVectorMap = new HashMap<>();

    public static void sessionInitialize(Long sessionPartnerId) {
        sessionKeyMap.put(sessionPartnerId, generateKey());
        initialVectorMap.put(sessionPartnerId, generateInitialVector());
    }

    public static void sessionInitialize(Long sessionPartnerId, SecretKey sessionKey, IvParameterSpec initialVector) {
        sessionKeyMap.put(sessionPartnerId, sessionKey);
        initialVectorMap.put(sessionPartnerId, initialVector);
    }

    public static void sessionDestroy(Long sessionPartnerId) {
        sessionKeyMap.remove(sessionPartnerId);
        initialVectorMap.remove(sessionPartnerId);
    }

    public static byte[] encrypt(Long sessionPartnerId, byte[] message, CipherType cipherType) {
        try {
            Cipher cipher = Cipher.getInstance(cipherType.getCipherName());
            if (cipherType == ECB) {
                cipher.init(Cipher.ENCRYPT_MODE, sessionKeyMap.get(sessionPartnerId));
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, sessionKeyMap.get(sessionPartnerId), initialVectorMap.get(sessionPartnerId));
            }
            return cipher.doFinal(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encrypt(byte[] message, CipherType cipherType, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(cipherType.getCipherName());
            if (cipherType == ECB) {
                cipher.init(Cipher.ENCRYPT_MODE, key);
            }
            return cipher.doFinal(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(Long sessionPartnerId, byte[] message, CipherType cipherType) {
        try {
            Cipher cipher = Cipher.getInstance(cipherType.getCipherName());
            if (cipherType == ECB) {
                cipher.init(Cipher.DECRYPT_MODE, sessionKeyMap.get(sessionPartnerId));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, sessionKeyMap.get(sessionPartnerId), initialVectorMap.get(sessionPartnerId));
            }
            return cipher.doFinal(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(byte[] message, CipherType cipherType, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(cipherType.getCipherName());
            if (cipherType == ECB) {
                cipher.init(Cipher.DECRYPT_MODE, key);
            }
            return cipher.doFinal(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SecretKey getSessionKeyBySessionPartnerId(Long sessionPartnerId) {
        return sessionKeyMap.get(sessionPartnerId);
    }

    public IvParameterSpec getInitialVectorBySessionPartnerId(Long sessionPartnerId) {
        return initialVectorMap.get(sessionPartnerId);
    }

    public static SecretKey getKey(byte[] key) {
        return new SecretKeySpec(key, "AES");
    }

    private static SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(KEY_SIZE);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static IvParameterSpec generateInitialVector() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private Aes() {

    }

}
