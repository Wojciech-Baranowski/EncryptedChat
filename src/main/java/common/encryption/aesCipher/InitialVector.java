package common.encryption.aesCipher;

import javax.crypto.spec.IvParameterSpec;
import java.io.Serializable;

public class InitialVector implements Serializable {

    private final byte[] initialVector;

    public InitialVector(byte[] initialVector) {
        this.initialVector = initialVector;
    }

    public IvParameterSpec getInitialVector() {
        return new IvParameterSpec(this.initialVector);
    }

}
