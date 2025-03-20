package org.example;


public class Main {
    public static void main(String[] args) {
        byte[] b = new byte[]{1, 0, 0};
        b = Utils.rotateLeft(b, 2);
        System.out.println(b[0]);
        System.out.println(b[1]);
        System.out.println(b[2]);

        DES des = new DES(new byte[] {2, 2, 2, 2, 2, 2, 2, 2});
        des.generateSubKeys();
        byte[] a = {1};
        byte[] c = {2};
        System.out.println(Utils.connectHalves(a, c)[0]);
        System.out.println(Utils.connectHalves(a, c)[1]);
    }
}