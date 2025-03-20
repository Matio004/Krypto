package org.example;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Schnorr {

    public BigInteger p, q, h, v, r, X, s1, s2;
    private BigInteger a;
    private static final int keySize = 512;
    private static final int qBits = 140;
    private static final Random random = new Random();

    MessageDigest digest;

    public Schnorr() {
        KeyGenerator();
        try{
            digest=MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
            a = new BigInteger(p.bitLength() - 1, random); // w przykladzie (qBits-2, random)
        } while (a.compareTo(BigInteger.ONE) <= 0 || a.compareTo(p.subtract(BigInteger.ONE)) >= 0);

        //v = h^a mod p
        v = h.modPow(a,p).modInverse(p);
    }

    // Generowanie podpisu na dokumencie M
    public BigInteger[] signGenerator(byte[] M){

        //  Wybierana jest losowa liczba r: 0 < r <= q-1
        do {
            r = new BigInteger(qBits, random);
        } while (r.compareTo(q) >= 0);

        //  Obliczane są:
        //  X = h^r mod p
        X = h.modPow(r,p);
        byte xBytes[] = X.toByteArray();

        //  s1 i s2 -> podpisem dla M będzie (s1,s2)
        //  s1 = funkcja haszująca konkatenacje M i X
        //  s2 = (r + a*s1) mod q

        // konkatenacja (con) to łączenie dwóch wyrażeń tekstowych w jedno
        byte con[] = new byte[M.length+ xBytes.length];

        for(int i = 0; i < M.length; i++) {
            con[i] = M[i];
        }

        for(int i = 0; i < xBytes.length; i++){
            con[M.length+i] = xBytes[i];
        }

        digest.update(con);

        s1 = new BigInteger(1, digest.digest());
        s2 = r.add(a.multiply(s1)).mod(q);

        return new BigInteger[]{s1, s2};

    }

    public BigInteger[] signGenerator(String M){

        do {
            r = new BigInteger(qBits, random);
        } while (r.compareTo(q) >= 0);

        X = h.modPow(r,p);

        String con = M;
        con += X.toString();
        digest.update(con.getBytes());
        s1 = new BigInteger(1, digest.digest());
        s2 = r.add(a.multiply(s1)).mod(q);

        return new BigInteger[]{s1, s2};

    }

}
