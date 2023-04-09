package app.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

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

    public static List<byte[]> byteArrayToByteFragmentList(byte[] byteArray, int fragmentSize) {
        List<byte[]> fragmentList = new ArrayList<>();
        for (int i = 0; i < byteArray.length / fragmentSize; i++) {
            byte[] fragment = new byte[fragmentSize];
            System.arraycopy(byteArray, i * fragmentSize, fragment, 0, fragmentSize);
            fragmentList.add(fragment);
        }
        if (byteArray.length % fragmentSize != 0) {
            byte[] lastFragment = new byte[fragmentSize];
            System.arraycopy(byteArray, (byteArray.length - byteArray.length % fragmentSize), lastFragment, 0, (byteArray.length % fragmentSize));
            fragmentList.add(lastFragment);
        }
        return fragmentList;
    }

    public static byte[] byteFragmentListToByteArray(List<byte[]> byteFragmentList) {
        int byteArraySize = 0;
        for (byte[] fragment : byteFragmentList) {
            byteArraySize += fragment.length;
        }
        byte[] byteArray = new byte[byteArraySize];
        for (int iterator = 0, i = 0; i < byteFragmentList.size(); i++) {
            System.arraycopy(byteFragmentList.get(i), 0, byteArray, iterator, byteFragmentList.get(i).length);
            iterator += byteFragmentList.get(i).length;
        }
        return byteArray;
    }

    private ArrayConverter() {

    }

}
