package app.utils;

public class BitUtils {

    public static int rightShift(int number, int shift) {
        return number >> shift;
    }

    public static int rightRotate(int number, int rotation) {
        int rotatedPart = (number << (Integer.SIZE - rotation));
        return rightShift(number, rotation) & rotatedPart;
    }

    public static int choose(int decisionNumber, int number1, int number2) {
        return (decisionNumber & number1) ^ ((~decisionNumber) & number2);
    }

    public static int major(int number1, int number2, int number3) {
        return (number1 & number2) ^ (number2 & number3) ^ (number3 & number1);
    }

}
