package common.encryption;

import app.utils.ArrayConverter;
import app.utils.LargePrimeGenerator;
import common.encryption.rsaKey.Key;
import lombok.Getter;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.*;

import static common.encryption.rsaKey.KeyConfig.PATH_TO_USER_PRIVATE_KEY;
import static common.encryption.rsaKey.KeyConfig.PATH_TO_USER_PUBLIC_KEY;
import static common.encryption.rsaKey.KeyType.PRIVATE;
import static common.encryption.rsaKey.KeyType.PUBLIC;

public class Rsa {

    private static final int KEY_BIT_SIZE = 2048;
    private static final int ENCRYPTED_BLOCK_SIZE = 128;

    private static Map<Long, Key> publicKeyMap;
    private static Key privateKey;
    @Getter
    private static Key publicKey;

    public static void initialize(byte[] key) {
        privateKey = new Key(PRIVATE, key);
        publicKey = new Key(PUBLIC, key);
        publicKeyMap = new HashMap<>();
    }

    public static byte[] encryptMessage(byte[] message, Key publicKey) {
        List<byte[]> fragmentedMessage = ArrayConverter.byteArrayToByteFragmentList(message, ENCRYPTED_BLOCK_SIZE - 1);
        List<byte[]> encryptedFragmentedMessage = new ArrayList<>();
        for (byte[] fragment : fragmentedMessage) {
            byte[] paddedMessage = new byte[fragment.length + 1];
            paddedMessage[0] = 1;
            System.arraycopy(fragment, 0, paddedMessage, 1, fragment.length);
            BigInteger bigIntegerMessage = new BigInteger(paddedMessage);
            BigInteger encryptedBigIntegerMessage = bigIntegerMessage.modPow(publicKey.getExponent(), publicKey.getModulo());
            byte[] encryptedFragment = encryptedBigIntegerMessage.toByteArray();
            encryptedFragmentedMessage.add(getPaddedEncryptedFragment(encryptedFragment));
        }
        return ArrayConverter.byteFragmentListToByteArray(encryptedFragmentedMessage);
    }

    public static byte[] decryptMessage(byte[] message) {
        List<byte[]> fragmentedMessage = ArrayConverter.byteArrayToByteFragmentList(message, KEY_BIT_SIZE / 8);
        List<byte[]> decryptedFragmentedMessage = new ArrayList<>();
        for (byte[] fragment : fragmentedMessage) {
            BigInteger bigIntegerMessage = new BigInteger(fragment);
            BigInteger decryptedBigIntegerMessage = bigIntegerMessage.modPow(privateKey.getExponent(), privateKey.getModulo());
            byte[] decryptedMessage = decryptedBigIntegerMessage.toByteArray();
            byte[] unpaddedMessage = new byte[decryptedMessage.length - 1];
            System.arraycopy(decryptedMessage, 1, unpaddedMessage, 0, decryptedMessage.length - 1);
            decryptedFragmentedMessage.add(unpaddedMessage);
        }
        return ArrayConverter.byteFragmentListToByteArray(decryptedFragmentedMessage);
    }

    public static boolean isKeyValid(byte[] key) {
        try {
            new Key(PRIVATE, key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Key getPublicKeyBySessionPartnerId(Long sessionPartnerId) {
        return publicKeyMap.get(sessionPartnerId);
    }

    public static Key addPublicKeyBySessionPartnerId(Long sessionPartnerId, Key key) {
        return publicKeyMap.put(sessionPartnerId, key);
    }

    public static void removePublicKeyBySessionPartnerId(Long sessionPartnerId) {
        publicKeyMap.remove(sessionPartnerId);
    }

    private static boolean keysExists() {
        File privateKeyFile = Paths.get(PATH_TO_USER_PRIVATE_KEY).toFile();
        File publicKeyFile = Paths.get(PATH_TO_USER_PUBLIC_KEY).toFile();
        return privateKeyFile.exists() && publicKeyFile.exists();
    }

    private static BigInteger calculatePublicExponent(BigInteger eulerPhi) {
        BigInteger publicExponentCandidate;
        do {
            publicExponentCandidate = new BigInteger(eulerPhi.bitLength() - 1, new Random());
        } while (!publicExponentCandidate.gcd(eulerPhi).equals(BigInteger.ONE));
        return publicExponentCandidate;
    }

    private static BigInteger calculatePrivateExponent(BigInteger publicExponent, BigInteger eulerPhi) {
        return publicExponent.modInverse(eulerPhi);
    }

    private static byte[] getPaddedEncryptedFragment(byte[] encryptedFragment) {
        if (encryptedFragment.length == KEY_BIT_SIZE / 8) {
            return encryptedFragment;
        } else {
            byte[] paddedEncryptedFragment = new byte[KEY_BIT_SIZE / 8];
            System.arraycopy(encryptedFragment, 0, paddedEncryptedFragment, KEY_BIT_SIZE / 8 - encryptedFragment.length, encryptedFragment.length);
            return paddedEncryptedFragment;
        }
    }

    private Rsa() {

    }

    public static void main(String[] args) {
        if (!keysExists()) {
            byte[] passwordHash = Sha256.hash(args[0]);
            BigInteger prime1 = LargePrimeGenerator.generatePrime(KEY_BIT_SIZE / 2 - 1);
            BigInteger prime2 = LargePrimeGenerator.generatePrime(KEY_BIT_SIZE / 2 - 1);
            BigInteger modulo = prime1.multiply(prime2);
            BigInteger eulerPhi = (prime1.subtract(BigInteger.ONE)).multiply(prime2.subtract(BigInteger.ONE));
            BigInteger publicExponent = calculatePublicExponent(eulerPhi);
            BigInteger privateExponent = calculatePrivateExponent(publicExponent, eulerPhi);
            privateKey = new Key(privateExponent, modulo, PRIVATE, passwordHash);
            publicKey = new Key(publicExponent, modulo, PUBLIC, passwordHash);
        }
    }

}
