package org.example;


public class Main {
    public static void main(String[] args) {
        DES des = new DES(new byte[] {0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37,
                0x38, 0x39, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46});
        byte[] b = new byte[] {0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68};
        for (byte value : b) {
            System.out.println(value);
        }
        b = des.encode(b);
        System.out.println();
        for (byte value : b) {
            System.out.println(value);
        }
        b = des.decode(b);
        System.out.println();
        for (byte value : b) {
            System.out.println(value);
        }
    }
}