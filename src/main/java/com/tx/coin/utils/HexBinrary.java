package com.tx.coin.utils;

/**
 * 十六进制和二进制转换
 * Created by guotx on 2017/10/12.
 */

public class HexBinrary {
    private HexBinrary() {
    }

    private static int hexToBin(char ch) {
        return 48 <= ch && ch <= 57?ch - 48:(65 <= ch && ch <= 70?ch - 65 + 10:(97 <= ch && ch <= 102?ch - 97 + 10:-1));
    }

    public static byte[] decodeHexBinrary(String s) {
        int len = s.length();
        if(len % 2 != 0) {
            return null;
        } else {
            byte[] out = new byte[len / 2];

            for(int i = 0; i < len; i += 2) {
                int h = hexToBin(s.charAt(i));
                int l = hexToBin(s.charAt(i + 1));
                if(h == -1 || l == -1) {
                    return null;
                }

                out[i / 2] = (byte)(h * 16 + l);
            }

            return out;
        }
    }

    private static char encode(int ch) {
        ch &= 15;
        return ch < 10?(char)(48 + ch):(char)(65 + (ch - 10));
    }

    public static String encodeHexBinrary(byte[] data) {
        StringBuffer r = new StringBuffer(data.length * 2);

        for(int i = 0; i < data.length; ++i) {
            r.append(encode(data[i] >> 4));
            r.append(encode(data[i] & 15));
        }

        return r.toString();
    }
}

