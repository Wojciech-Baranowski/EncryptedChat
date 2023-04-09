package app.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class ArrayConverter {

    public static int[] byteArrayToIntArray(byte[] byteArray) {
        IntBuffer intBuffer = ByteBuffer.wrap(byteArray)
                .order(ByteOrder.BIG_ENDIAN)
                .asIntBuffer();
        int[] intArray = new int[intBuffer.remaining()];
        intBuffer.get(intArray);
        return intArray;
    }

    public static byte[] intArrayToByteArray(int[] intArray) {
        byte[] byteArray = new byte[intArray.length * Integer.BYTES];
        for (int i = 0; i < intArray.length; i++) {
            byte[] block = intToByteArray(intArray[i]);
            System.arraycopy(block, 0, byteArray, i * Integer.BYTES, Integer.BYTES);
        }
        return byteArray;
    }

    public static byte[] intToByteArray(int integer) {
        return BigInteger.valueOf(integer).toByteArray();
    }

}
