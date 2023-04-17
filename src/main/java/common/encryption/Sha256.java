package common.encryption;

import app.utils.ArrayConverter;
import app.utils.BitUtils;
import common.Serializer;

import java.util.Arrays;
import java.util.List;

public class Sha256 {

    private static final int[] INITIAL_HASH_BLOCK_VALUES = {0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19};
    private static final int[] ROUND_CONSTANTS = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2};
    private static final int FRAGMENT_SIZE = 64;
    private static final int NUMBER_OF_HASH_BLOCKS = 8;

    public static byte[] hash(Object object) {
        int[] hashBlocks = getInitializedHashBlocks();
        List<byte[]> fragments = getPreprocessedDataFragments(object);
        for (byte[] fragment : fragments) {
            processFragment(hashBlocks, fragment);
        }
        return ArrayConverter.intArrayToByteArray(hashBlocks);
    }

    private static int[] getInitializedHashBlocks() {
        int[] hashBlocks = new int[NUMBER_OF_HASH_BLOCKS];
        System.arraycopy(INITIAL_HASH_BLOCK_VALUES, 0, hashBlocks, 0, NUMBER_OF_HASH_BLOCKS);
        return hashBlocks;
    }

    private static List<byte[]> getPreprocessedDataFragments(Object object) {
        byte[] dataToHash = Serializer.serialize(object);
        byte[] paddedData = addPadding(dataToHash);
        return ArrayConverter.byteArrayToByteFragmentList(paddedData, FRAGMENT_SIZE);
    }

    private static byte[] addPadding(byte[] data) {
        int dataLength = data.length;
        int paddedDataLength = dataLength - (dataLength % FRAGMENT_SIZE) + FRAGMENT_SIZE;
        byte[] bytePaddedDataLength = ArrayConverter.intToByteArray(paddedDataLength);
        byte[] paddedData = new byte[paddedDataLength];
        System.arraycopy(data, 0, paddedData, 0, dataLength);
        paddedData[dataLength] = (byte) (1 << 7);
        Arrays.fill(paddedData, dataLength + 1, paddedDataLength - bytePaddedDataLength.length, (byte) 0);
        System.arraycopy(bytePaddedDataLength, 0, paddedData, paddedDataLength - bytePaddedDataLength.length, bytePaddedDataLength.length);
        return paddedData;
    }

    private static void processFragment(int[] hashBlocks, byte[] fragment) {
        int[] words = getInitializedWords(fragment);
        int[] extendedWords = getExtendedWords(words);
        int[] currentHashBlocks = Arrays.copyOf(hashBlocks, NUMBER_OF_HASH_BLOCKS);
        compressHashBlocks(currentHashBlocks, extendedWords);
        for (int i = 0; i < NUMBER_OF_HASH_BLOCKS; i++) {
            hashBlocks[i] += currentHashBlocks[i];
        }
    }

    private static int[] getInitializedWords(byte[] fragment) {
        int[] convertedFragment = ArrayConverter.byteArrayToIntArray(fragment);
        int[] words = new int[FRAGMENT_SIZE];
        System.arraycopy(convertedFragment, 0, words, 0, convertedFragment.length);
        Arrays.fill(words, FRAGMENT_SIZE / Integer.BYTES, FRAGMENT_SIZE, 0);
        return words;
    }

    private static int[] getExtendedWords(int[] words) {
        for (int i = FRAGMENT_SIZE / Integer.BYTES; i < FRAGMENT_SIZE; i++) {
            int s0 = (BitUtils.rightRotate(words[i - 15], 7)) ^ (BitUtils.rightRotate(words[i - 15], 18)) ^ (BitUtils.rightShift(words[i - 15], 3));
            int s1 = (BitUtils.rightRotate(words[i - 2], 17)) ^ (BitUtils.rightRotate(words[i - 2], 19)) ^ (BitUtils.rightShift(words[i - 2], 10));
            words[i] = words[i - 16] + s0 + words[i - 7] + s1;
        }
        return words;
    }

    private static void compressHashBlocks(int[] hashBlocks, int[] words) {
        for (int i = 0; i < FRAGMENT_SIZE; i++) {
            int s0 = BitUtils.rightRotate(hashBlocks[0], 2) ^ BitUtils.rightRotate(hashBlocks[0], 13) ^ BitUtils.rightRotate(hashBlocks[0], 22);
            int s1 = BitUtils.rightRotate(hashBlocks[4], 6) ^ BitUtils.rightRotate(hashBlocks[4], 11) ^ BitUtils.rightRotate(hashBlocks[4], 25);
            int temp1 = hashBlocks[7] + s1 + BitUtils.choose(hashBlocks[4], hashBlocks[5], hashBlocks[6]) + ROUND_CONSTANTS[i] + words[i];
            int temp2 = s0 + BitUtils.major(hashBlocks[0], hashBlocks[1], hashBlocks[2]);
            hashBlocks[7] = hashBlocks[6];
            hashBlocks[6] = hashBlocks[5];
            hashBlocks[5] = hashBlocks[4];
            hashBlocks[4] = hashBlocks[3] + temp1;
            hashBlocks[3] = hashBlocks[2];
            hashBlocks[2] = hashBlocks[1];
            hashBlocks[1] = hashBlocks[0];
            hashBlocks[0] = temp1 + temp2;
        }
    }

    private Sha256() {

    }

}
