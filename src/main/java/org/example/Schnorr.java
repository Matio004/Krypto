package org.example;

import java.lang.*;
import java.util.*;
import java.math.BigInteger;
import java.security.*;

public class Schnorr {

    public BigInteger a, p, q, h, v, r, X, s1, s2, Z;

    private static final int keySize = 512;
    private static final int  qBits = 140;
    private static final Random random = new Random();
    MessageDigest digest;

    private void concatenation(byte[] M, byte[] xBytes) {
        byte[] con = new byte[M.length+xBytes.length];

        System.arraycopy(M, 0, con, 0, M.length);

        for (int i=0, j=0; j<xBytes.length; i++,j++){
            con[M.length+i]=xBytes[j];
        }

        digest.update(con);
    }

    public Schnorr()
    {
        KeyGenerator();
        try{
            digest=MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    public void KeyGenerator()
    {
        q=BigInteger.probablePrime(qBits,new Random());
        BigInteger pom;

        // => p = kq + 1
        do {
            p = BigInteger.probablePrime(keySize,new Random());
            pom = p.subtract(BigInteger.ONE);
            p = p.subtract(pom.remainder(q));
        } while (!p.isProbablePrime(2));

        // h^q mod p != 1
        h = new BigInteger(keySize - 2, random);
        h = h.modPow(p.subtract(BigInteger.ONE).divide(q), p);

        // 1 < a < p-1 -> warunek z wykładu???
        do {
            a = new BigInteger(qBits - 2, random);
        } while (!(a.compareTo(BigInteger.ONE) == 1));

        //v = h^a mod p
        v=h.modPow(a,p).modInverse(p);


    }


    // Generowanie podpisu na dokumencie M
    public BigInteger[] signGenerator(byte[] M)
    {
        do {
            r = new BigInteger(qBits, random);
        } while (r.compareTo(q) >= 0);

        //  Obliczane są:
        //  X = h^r mod p
        X = h.modPow(r, p);
        byte[] xBytes = X.toByteArray();

        // konkatenacja (con) to łączenie dwóch wyrażeń tekstowych w jedno
        concatenation(M, xBytes);

        s1 =new BigInteger(1, digest.digest());
        s2 =r.add(a.multiply(s1)).mod(q);

        return new BigInteger[]{s1,s2};
    }

    public BigInteger[] signGenerator(String M)
    {
        r = new BigInteger(160-2, random);
        X = h.modPow(r, p);

        String con=M;
        con+= X.toString();
        digest.update(con.getBytes());

        s1 =new BigInteger(1, digest.digest());
        s2 =r.add(a.multiply(s1)).mod(q);

        return new BigInteger[]{s1,s2};
    }

    public boolean signVerificator(byte[] M, BigInteger[] sign)
    {
        //  Obliczanie Z = h^(s2) * v^(s1) mod p
        s1 = sign[0];
        s2 = sign[1];

        Z = h.modPow(s2, p).multiply(v.modPow(s1, p)).mod(p);

        //  konkatencja Z i M
        byte[] con =Z.toByteArray();
        concatenation(M, con);
        BigInteger hash=new BigInteger(1, digest.digest());

        return hash.compareTo(s1) == 0;
    }

    public boolean stringSignVerificator(String M, String sign)
    {
        String[] tab =sign.split("\n");

        BigInteger s1=new BigInteger(1,Utils.hexToBytes(tab[0]));
        BigInteger s2=new BigInteger(1,Utils.hexToBytes(tab[1]));

        Z = h.modPow(s2, p).multiply(v.modPow(s1, p)).mod(p);

        String con = M + Z;
        digest.update(con.getBytes());
        BigInteger f = new BigInteger(1, digest.digest());

        return f.compareTo(s1) == 0;
    }


}
