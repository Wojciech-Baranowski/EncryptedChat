package app.encryption;

import app.encryption.key.Key;
import app.utils.ArrayConverter;
import app.utils.LargePrimeGenerator;
import lombok.Getter;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static app.encryption.key.KeyConfig.PATH_TO_USER_PRIVATE_KEY;
import static app.encryption.key.KeyConfig.PATH_TO_USER_PUBLIC_KEY;
import static app.encryption.key.KeyType.PRIVATE;
import static app.encryption.key.KeyType.PUBLIC;

@Getter
public class Rsa {

    private static final int KEY_BIT_SIZE = 2048;
    private static final int ENCRYPTED_BLOCK_SIZE = 128;

    private final Key privateKey;
    private final Key publicKey;

    public Rsa() {
        if (keysExists()) {
            this.privateKey = new Key(PRIVATE);
            this.publicKey = new Key(PUBLIC);
        } else {
            BigInteger prime1 = LargePrimeGenerator.generatePrime(KEY_BIT_SIZE / 2 - 1);
            BigInteger prime2 = LargePrimeGenerator.generatePrime(KEY_BIT_SIZE / 2 - 1);
            BigInteger modulo = prime1.multiply(prime2);
            BigInteger eulerPhi = (prime1.subtract(BigInteger.ONE)).multiply(prime2.subtract(BigInteger.ONE));
            BigInteger publicExponent = calculatePublicExponent(eulerPhi);
            BigInteger privateExponent = calculatePrivateExponent(publicExponent, eulerPhi);
            this.privateKey = new Key(privateExponent, modulo, PRIVATE);
            this.publicKey = new Key(publicExponent, modulo, PUBLIC);
        }
    }

    public byte[] encryptMessage(byte[] message) {
        List<byte[]> fragmentedMessage = ArrayConverter.byteArrayToByteFragmentList(message, ENCRYPTED_BLOCK_SIZE - 1);
        List<byte[]> encryptedFragmentedMessage = new ArrayList<>();
        for (byte[] fragment : fragmentedMessage) {
            byte[] paddedMessage = new byte[fragment.length + 1];
            paddedMessage[0] = 1;
            System.arraycopy(fragment, 0, paddedMessage, 1, fragment.length);
            BigInteger bigIntegerMessage = new BigInteger(paddedMessage);
            BigInteger encryptedBigIntegerMessage = bigIntegerMessage.modPow(this.publicKey.getExponent(), this.publicKey.getModulo());
            byte[] encryptedFragment = encryptedBigIntegerMessage.toByteArray();
            encryptedFragmentedMessage.add(getPaddedEncryptedFragment(encryptedFragment));
        }
        return ArrayConverter.byteFragmentListToByteArray(encryptedFragmentedMessage);
    }

    public byte[] decryptMessage(byte[] message) {
        List<byte[]> fragmentedMessage = ArrayConverter.byteArrayToByteFragmentList(message, KEY_BIT_SIZE / 8);
        List<byte[]> decryptedFragmentedMessage = new ArrayList<>();
        for (byte[] fragment : fragmentedMessage) {
            BigInteger bigIntegerMessage = new BigInteger(fragment);
            BigInteger decryptedBigIntegerMessage = bigIntegerMessage.modPow(this.privateKey.getExponent(), this.privateKey.getModulo());
            byte[] decryptedMessage = decryptedBigIntegerMessage.toByteArray();
            byte[] unpaddedMessage = new byte[decryptedMessage.length - 1];
            System.arraycopy(decryptedMessage, 1, unpaddedMessage, 0, decryptedMessage.length - 1);
            decryptedFragmentedMessage.add(unpaddedMessage);
        }
        return ArrayConverter.byteFragmentListToByteArray(decryptedFragmentedMessage);
    }

    private boolean keysExists() {
        File privateKeyFile = Paths.get(PATH_TO_USER_PRIVATE_KEY).toFile();
        File publicKeyFile = Paths.get(PATH_TO_USER_PUBLIC_KEY).toFile();
        return privateKeyFile.exists() && publicKeyFile.exists();
    }

    private BigInteger calculatePublicExponent(BigInteger eulerPhi) {
        BigInteger publicExponentCandidate;
        do {
            publicExponentCandidate = new BigInteger(eulerPhi.bitLength() - 1, new Random());
        } while (!publicExponentCandidate.gcd(eulerPhi).equals(BigInteger.ONE));
        return publicExponentCandidate;
    }

    private BigInteger calculatePrivateExponent(BigInteger publicExponent, BigInteger eulerPhi) {
        return publicExponent.modInverse(eulerPhi);
    }

    private byte[] getPaddedEncryptedFragment(byte[] encryptedFragment) {
        if (encryptedFragment.length == KEY_BIT_SIZE / 8) {
            return encryptedFragment;
        } else {
            byte[] paddedEncryptedFragment = new byte[KEY_BIT_SIZE / 8];
            System.arraycopy(encryptedFragment, 0, paddedEncryptedFragment, KEY_BIT_SIZE / 8 - encryptedFragment.length, encryptedFragment.length);
            return paddedEncryptedFragment;
        }
    }

}
