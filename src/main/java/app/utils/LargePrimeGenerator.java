package app.utils;

import java.math.BigInteger;
import java.util.Random;

public class LargePrimeGenerator {

    private static final int CERTAINTY = 100;

    public static BigInteger generatePrime(int bitSize) {
        return new BigInteger(bitSize, CERTAINTY, new Random());
    }

    private LargePrimeGenerator() {

    }

}
