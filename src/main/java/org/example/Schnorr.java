package org.example;

import java.math.BigInteger;
import java.util.Random;

public class Schnorr {

    public BigInteger p, q, h, v;
    private BigInteger a;
    private static final int keySize = 512;
    private static final int qBits = 140;
    private static final Random random = new Random();

    public Schnorr() {
        KeyGenerator();
    }

    public void KeyGenerator() {
        //  Wybierane są:
        //  liczba pierwsza q: q > 2^140
        //  liczba pierwsza p: (p-1)/q nalezy do naturalnych i p > 2^512
        //  generator h (!=1) taki, że h^q mod p != 1
        //  klucz prywatny a
        //  klucz publiczny v

        q = BigInteger.probablePrime(qBits, random); //Schnorr qBits = 140

        // => p = kq + 1
        do {
            BigInteger k = new BigInteger(keySize - qBits, random);
            p = k.multiply(q).add(BigInteger.ONE);
        } while (!p.isProbablePrime(2));

        // h^q mod p != 1
        do {
            h = new BigInteger(keySize -2, random);
        } while (h.modPow(q,p).equals(BigInteger.ONE));

        // 1 < a < p-1 -> warunek z wykładu
        do {
            a = new BigInteger(p.bitLength() - 1, random);
        } while (a.compareTo(BigInteger.ONE) <= 0 || a.compareTo(p.subtract(BigInteger.ONE)) >= 0);

        //v = h^a mod p
        v = h.modPow(a,p).modInverse(p);
    }

}
