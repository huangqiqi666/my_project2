package com.hikvision.pbg.sitecodeprj.utils.humanId;

import java.util.HashMap;
import java.util.Map;

public class IdentifySecurityUtil {
    private static final byte[] v = new byte[]{54, 78, 73, 113, 83, 81, 69, 67, 88, 56, 68, 79, 102, 65, 70, 114, 119, 74, 85, 50, 57, 90, 115, 72, 52, 107, 106, 117, 99, 100, 120, 116, 66, 118, 112, 53, 111, 71, 49, 97, 101, 77, 109, 105, 82, 87, 103, 110, 51, 89, 98, 122, 76, 104, 84, 108, 55, 86, 75, 48, 121, 80};
    private static final Map<Byte, Integer> rvMap;
    private static final int x = 2;

    public IdentifySecurityUtil() {
    }

    protected static String decode(String value) {
        byte[] bvalue = value.getBytes();
        byte[] decodeValue2 = decode2(bvalue);
        byte[] decodeValue1 = decode2(decodeValue2);
        byte[] decodeValue = decode1(decodeValue1);
        return new String(decodeValue, 0, decodeValue.length);
    }

    protected static String encode(String value) {
        byte[] bvalue = value.getBytes();
        byte[] encodeValue = encode1(bvalue);
        byte[] encodeValue1 = encode2(encodeValue);
        byte[] encodeValue2 = encode2(encodeValue1);
        return new String(encodeValue2, 0, encodeValue2.length);
    }

    private static byte[] decode2(byte[] encodeValue) {
        int c = encodeValue.length / 2;
        int m = encodeValue.length % 2;
        if (m > 0) {
            ++c;
        }

        byte[] rs = new byte[encodeValue.length];

        for(int i = 0; i < encodeValue.length; ++i) {
            byte vint = encodeValue[i];
            rs[i % c * 2 + i / c] = vint;
        }

        return rs;
    }

    private static byte[] encode2(byte[] encodeValue) {
        int c = encodeValue.length / 2;
        int m = encodeValue.length % 2;
        byte[][] t = new byte[2][];

        int i;
        for(i = 0; i < t.length; ++i) {
            if (i < m) {
                t[i] = new byte[c + 1];
            } else {
                t[i] = new byte[c];
            }
        }

        for(i = 0; i < encodeValue.length; ++i) {
            t[i % 2][i / 2] = encodeValue[i];
        }

        byte[] rs = new byte[encodeValue.length];
        int iindex = 0;
        byte[][] var6 = t;
        int var7 = t.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            byte[] is = var6[var8];
            System.arraycopy(is, 0, rs, iindex, is.length);
            iindex += is.length;
        }

        return rs;
    }

    private static byte[] encode1(byte[] bvalue) {
        byte[] ivalue = new byte[bvalue.length];

        for(int i = 0; i < bvalue.length; ++i) {
            byte b = bvalue[i];
            if (b >= 48 && b <= 57) {
                ivalue[i] = v[b - 48];
            } else if (b >= 65 && b <= 90) {
                ivalue[i] = v[b - 65 + 10];
            } else if (b >= 97 && b <= 122) {
                ivalue[i] = v[b - 97 + 36];
            } else {
                ivalue[i] = b;
            }
        }

        return ivalue;
    }

    private static byte[] decode1(byte[] bvalue) {
        byte[] ivalue = new byte[bvalue.length];

        for(int i = 0; i < bvalue.length; ++i) {
            int b = bvalue[i];
            Integer v = (Integer)rvMap.get((byte)b);
            if (v == null) {
                ivalue[i] = (byte)b;
            } else {
                int iv = v;
                if (iv >= 0 && iv <= 9) {
                    ivalue[i] = (byte)(iv + 48);
                } else if (iv >= 10 && iv <= 35) {
                    ivalue[i] = (byte)(iv - 10 + 65);
                } else if (iv >= 36 && iv <= 62) {
                    ivalue[i] = (byte)(iv - 36 + 97);
                }
            }
        }

        return ivalue;
    }

    static {
        rvMap = new HashMap(v.length);

        for(int i = 0; i < v.length; ++i) {
            rvMap.put(v[i], i);
        }

    }
}