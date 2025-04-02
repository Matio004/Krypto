package org.example;


public class Utils {

    public static byte[] selectBits(byte[] array, byte[] bits) {
        byte[] bytes = new byte[(bits.length % 8 == 0) ? bits.length/8 : bits.length/8 + 1];

        for (int i = 0; i < bits.length; i++) {
            setBit(bytes, i, isBitSet(array, bits[i]));
        }
        return bytes;
    }

    public static boolean isBitSet(byte b, int n) {
        return (b & (1 << n)) != 0;
    }

    public static boolean isBitSet(byte[] bytes, int n) {
        return isBitSet(bytes[n / 8], n % 8);
    }

    public static void setBit(byte[] bytes, int n, boolean value) {
        int posByte = n / 8;
        int postBit = n % 8;
        bytes[posByte] = setBit(bytes[posByte], postBit, value);
    }

    public static byte setBit(byte b, int n, boolean value) {
        if (value) {
            return (byte) (b | (1 << n));
        }
        return (byte) (b & ~(1 << n));
    }

    public static byte[] rotateLeft(byte[] array, int n) {
        byte[] temp = array.clone();

        for (int i = n; i < array.length * 8 + n; i++) {
            setBit(temp, i - n, isBitSet(array, i % (array.length * 8)));
        }
        return temp;
    }

    public static byte[] connectHalves(byte[] h0, byte[] h1) {
        byte[] temp = new byte[h0.length + h1.length];

        System.arraycopy(h0, 0, temp, 0, h0.length);
        System.arraycopy(h1, 0, temp, h0.length, h1.length);
        return temp;
    }

    public static byte[] xor(byte[] b1, byte[] b2) {
        byte[] temp = new byte[b1.length];

        for (int i = 0; i < temp.length; i++) {
            temp[i] = (byte) (b1[i] ^ b2[i]);
        }
        return temp;
    }

    public static byte hexFromChar(char chr) {
        return switch (chr) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> (byte) (chr - 48);
            case 'a', 'A' -> 10;
            case 'b', 'B' -> 11;
            case 'c', 'C' -> 12;
            case 'd', 'D' -> 13;
            case 'e', 'E' -> 14;
            case 'f', 'F' -> 15;
            default -> -1;
        };
    }
}
