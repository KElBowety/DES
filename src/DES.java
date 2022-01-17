import java.math.BigInteger;
import java.util.Locale;

public class DES {
    private final int[] PC1C = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36
    };

    private final int[] PC1D = {
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };

    private final int[] PC2 = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

    private final int[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    private final int[] IIP = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    private final int[] E = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };

    private final int[] P = {
            16, 7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9,
            19, 13, 30, 6,
            22, 11, 4, 25
    };

    private final int[][][] S = {
            {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            },
            {
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            },
            {
                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            },
            {
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            },
            {
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
            },
            {
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            },
            {
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
            },
            {
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            }
    };

    private String cipher, key;

    private String[] keys;

    StringBuilder builder;

    public DES(String cipher, String key) {
        builder = new StringBuilder();
        this.cipher = cipher;
        this.key = key;
        keys = new String[16];
        generateKeys();
    }

    public String decrypt() {
        String x = permute(cipher, IP, 16);

        String l = x.substring(0, 32);
        String r = x.substring(32);
        String temp;

        for(int i = 15; i >= 0; i--) {
            temp = r;
            r = xor(f(r, keys[i]), l);
            l = temp;
        }

        builder.setLength(0);
        builder.append(r).append(l);
        System.out.println(builder.toString().length());

        x = permute(builder.toString(), IIP, 2);

        return Long.toString(Long.parseUnsignedLong(x, 2), 16).toUpperCase();
    }

    private void generateKeys() {
        String c = permute(key, PC1C, 16);
        String d = permute(key, PC1D, 16);

        for(int i = 1; i <= 16; i++) {
            if(i == 1 || i == 2 || i == 9 || i == 16) {
                c = leftShift(c);
                d = leftShift(d);
            }
            else {
                c = leftShift(leftShift(c));
                d = leftShift(leftShift(d));
            }

            String concat = c + d;
            builder.setLength(0);

            for (int j : PC2) {
                builder.append(concat.charAt(j - 1));
            }

            keys[i - 1] = builder.toString();
        }
    }

    private String permute(String input, int[] table, int radix) {
        builder.setLength(0);
        String binary = Long.toBinaryString(Long.parseUnsignedLong(input, radix));

        if(binary.length() < 64) {
            int len = 64 - binary.length();
            for(int i = 0; i < len; i++) {
                binary = "0" + binary;
            }
        }

        for (int i : table) {
            builder.append(binary.charAt(i - 1));
        }

        return builder.toString();
    }

    private String leftShift(String n) {
        return n.substring(1) + n.charAt(0);
    }

    private String f(String r, String k) {
        builder.setLength(0);

        for(int i : E) {
            builder.append(r.charAt(i - 1));
        }

        String xor = xor(builder.toString(), k);
        builder.setLength(0);

        for(int i = 0; i < 8; i++) {
           builder.append(sBox(i, xor.substring((i * 6), (i * 6 + 6))));
        }

        String c = builder.toString();
        builder.setLength(0);

        for(int i : P) {
            builder.append(c.charAt(i - 1));
        }

        return builder.toString();
    }

    private String sBox(int i, String b) {
        String rString = b.charAt(0) + "" + b.charAt(5);
        String cString = b.substring(1, 5);

        int r = Integer.parseInt(rString, 2);
        int c = Integer.parseInt(cString, 2);

        String result = Integer.toBinaryString(S[i][r][c]);

        if(result.length() < 4) {
            int len = 4 - result.length();
            for(int j = 0; j < len; j++) {
                result = "0" + result;
            }
        }

        return result;
    }

    private String xor(String s1, String s2) {
        BigInteger big = new BigInteger(s1, 2);
        big = big.xor(new BigInteger(s2, 2));

        String result = big.toString(2);

        if(result.length() < s1.length()) {
            int len = s1.length() - result.length();
            for(int j = 0; j < len; j++) {
                result = "0" + result;
            }
        }

        return result;
    }
}
