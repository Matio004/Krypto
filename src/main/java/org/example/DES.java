package org.example;

public class DES {
    private final byte[] key;
    private byte[][] subKeys;
    private final byte[][] sBox = {
            {
                14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, // S1
                0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
                4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
                15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13
            },
            {
                15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, // S2
                3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
                0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
                13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9
            },
            {
                10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, // S3
                13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
                13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
                1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12
            },
            {
                7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, // S4
                13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
                10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
                3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14
            },
            {
                2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, // S5
                14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
                4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
                11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3
            },
            {
                12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, // S6
                10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
                9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
                4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13
            },
            {
                4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, // S7
                13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
                1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
                6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12
            },
            {
                13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, // S8
                1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
                7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
                2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
            }
    };
    private final byte[] pBox = {
            15, 6, 19, 20, 28, 11, 27, 16, 0, 14, 22, 25, 4, 17, 30, 9,
            1, 7, 23, 13, 31, 26, 2, 8, 18, 12, 29, 5, 21, 10, 3, 24
    };

    private final byte[] ip = {
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7,
            56, 48, 40, 32, 24, 16, 8, 0,
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6
    };

    private final byte[] ipInv = {
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25,
            32, 0, 40, 8, 48, 16, 56, 24
    };

    public DES(byte[] key) {
        this.key = key;
        generateSubKeys();
    }

    public DES(String key) {
        this.key = new byte[8];
        byte[] temp = key.getBytes();
        for (int i = 0; i < this.key.length; i++) {
            this.key[i] = (byte) (Utils.hexFromChar((char) temp[2*i]) << 4 | Utils.hexFromChar((char) temp[2 * i + 1]));
        }
        generateSubKeys();
    }

    private void generateSubKeys() {
        this.subKeys = new byte[16][];

        byte[] halfL = Utils.selectBits(
                key, new byte[] {
                        57, 49, 41, 33, 25, 17, 9,
                        1, 58, 50, 42, 34, 26, 18,
                        10, 2, 59, 51, 43, 35, 27,
                        19, 11, 3, 60, 52, 44, 36
                }
        );
        byte[] halfR = Utils.selectBits(
                key, new byte[] {
                        63, 55, 47, 39, 31, 23, 15,
                        7, 62, 54, 46, 38, 30, 22,
                        14, 6, 61, 53, 45, 37, 29,
                        21, 13, 5, 28, 20, 12, 4
                }
        );
        int[] rotateLeftPos = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
        byte[] pBox = new byte[] {
                14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10,
                23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2,
                41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48,
                44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32
        };


        for (int i = 0; i < 16; i++) {
            subKeys[i] = Utils.selectBits(Utils.connectHalves(
                    Utils.rotateLeft(halfL, rotateLeftPos[i]),
                    Utils.rotateLeft(halfR, rotateLeftPos[i])
            ), pBox);
        }

    }

    private byte[] doSBox(byte[] bytes) {
        byte row;
        byte col;
        byte[] out = new byte[4];
        byte half;

        for (int i = 0; i < sBox.length; i++) {
            row = Utils.selectBits(bytes, new byte[] {(byte) (i * 6), (byte) (i * 6 + 5)})[0];
            col = Utils.selectBits(bytes, new byte[] {
                    (byte) (i * 6 + 1),
                    (byte) (i * 6 + 2),
                    (byte) (i * 6 + 3),
                    (byte) (i * 6 + 4)})[0];
            half = sBox[i][row * 16 + col];
            if (i % 2 != 0) {
                out[i / 2] <<= 4;
            }
            out[i / 2] |= half;
        }
        return out;
    }

    private byte[] encodeBlock(byte[] bytes) {
        bytes = Utils.selectBits(bytes, ip);

        byte[] r = rightBlock(bytes);
        byte[] l = leftBlock(bytes);

        for (int i = 0; i < 16; i++) {
            byte[] temp = r;
            r = extendBlock(r);
            r = Utils.xor(r, subKeys[i]);
            r = doSBox(r);
            r = Utils.selectBits(r, pBox);
            r = Utils.xor(l, r);
            l = temp;
        }

        return Utils.selectBits(Utils.connectHalves(r, l), ipInv);
    }

    private byte[] decodeBlock(byte[] bytes) {
        bytes = Utils.selectBits(bytes, ip);
        byte[] r = rightBlock(bytes);
        byte[] l = leftBlock(bytes);

        for (int i = 15; i >= 0; i--) {
            byte[] temp = r;
            r = extendBlock(r);
            r = Utils.xor(r, subKeys[i]);
            r = doSBox(r);
            r = Utils.selectBits(r, pBox);
            r = Utils.xor(l, r);
            l = temp;
        }

        return Utils.selectBits(Utils.connectHalves(r, l), ipInv);
    }

    public byte[] encode(byte[] bytes) {
        int extent = bytes.length % 8;
        int resultsLength = (bytes.length / 8 + 1) * 8;
        byte[] results = new byte[resultsLength];

        for (int i = 0; i < resultsLength / 8; i++) {
            byte[] block = new byte[8];
            try {
                System.arraycopy(bytes, i * 8, block, 0, block.length);
            }
            catch (ArrayIndexOutOfBoundsException _) {
                System.arraycopy(bytes, i * 8, block, 0, extent);
                System.arraycopy(new byte[] {(byte) (8 - extent)},0, block, 7, 1);
            }
            System.arraycopy(encodeBlock(block), 0, results, i * 8, block.length);
        }

        return results;
    }

    public byte[] decode(byte[] bytes) {
        if (bytes.length % 8 != 0) {
            throw new RuntimeException("Nie można odszyfrować");
        }

        byte[] results = new byte[bytes.length];

        for (int i = bytes.length / 8 - 1; i >= 0; i--) {
            byte[] block = new byte[8];
            System.arraycopy(bytes, i * 8, block, 0, block.length);
            if (i == bytes.length / 8 - 1) {
                block = decodeBlock(block);
                results = new byte[bytes.length - block[7]];
                System.arraycopy(block, 0, results, bytes.length - block[7] - (8 - block[7]), 8 - block[7]);
            }
            else {
                System.arraycopy(decodeBlock(block), 0, results, i * 8, block.length);
            }
        }
        return results;
    }

    private byte[] leftBlock(byte[] in) {
        byte[] out = new byte[in.length/2];
        System.arraycopy(in, 0, out, 0, out.length);
        return out;
    }

    private byte[] rightBlock(byte[] in) {
        byte[] out = new byte[in.length/2];
        System.arraycopy(in, in.length/2, out, 0, out.length);
        return out;
    }

    private byte[] extendBlock(byte[] in) {
        byte[] extendBlock = new byte[] {
                31, 0, 1, 2, 3, 4,
                3, 4, 5, 6, 7, 8,
                7, 8, 9, 10, 11, 12,
                11, 12, 13, 14, 15, 16,
                15, 16, 17, 18, 19, 20,
                19, 20, 21, 22, 23, 24,
                23, 24, 25, 26, 27, 28,
                27, 28, 29, 30, 31, 0
        };
        return Utils.selectBits(in, extendBlock);
    }

}
