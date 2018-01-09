package com.tx.coin.utils;

/**
 * Created by guotx on 2017/10/12.
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by guotx on 2017/5/12 14:17.
 */
public class EncryptHelper {
    /**
     * MD5签名
     * @throws IOException
     */
    public static String md5(String content) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
        byte[] byteArray = Digests.md5(inputStream);
        return HexBinrary.encodeHexBinrary(byteArray).toLowerCase();
    }

    /**
     * MD5 加密
     *
     * @param content
     * @return
     * @throws IOException
     */
    public static String md5(String content, String enc) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                content.getBytes(enc));
        byte[] byteArray = Digests.md5(inputStream);
        return HexBinrary.encodeHexBinrary(byteArray).toLowerCase();
    }
}