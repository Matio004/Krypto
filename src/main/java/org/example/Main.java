package org.example;
import java.math.BigInteger;


public class Main {
    public static void main(String[] args) {
        DES des = new DES(new byte[]{0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37,
                0x38, 0x39, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46});
        byte[] b = new byte[]{0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68};
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

        // Schnorr
        Schnorr schnorr = new Schnorr();

        String message = "Text";
        byte[] messageBytes = message.getBytes();
        BigInteger[] signature = schnorr.signGenerator(messageBytes);

        System.out.println("Wygenerowany podpis:");
        System.out.println("s1: " + signature[0].toString(16)); // Wypisanie s1 w formacie hex
        System.out.println("s2: " + signature[1].toString(16)); // Wypisanie s2 w formacie hex

        boolean isVerified = schnorr.signVerificator(messageBytes, signature);
        if (isVerified) {
            System.out.println("Podpis jest poprawny.");
        } else {
            System.out.println("Podpis jest niepoprawny.");
        }

        //TODO podpis String
    }
    }
