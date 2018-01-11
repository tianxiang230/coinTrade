package com.tx.coin.utils;

/**
 * Created by guotx on 2017/10/12.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by guotx on 2017/5/12 14:17.
 */
public class EncryptHelper {

    private static Logger logger = LoggerFactory.getLogger(EncryptHelper.class);

    /**
     * MD5签名
     *
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

    public static Set<String> toSortSet(Map<String, String> map, String joinStr) {
        if (map.size() <= 0) {
            return null;
        }
        Set<String> paramSet = new TreeSet<>();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet()
                .iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entity = iterator.next();
            paramSet.add(entity.getKey() + joinStr + entity.getValue());
        }
        return paramSet;
    }

    /**
     * 将排序的key-value连接成query string
     *
     * @param collection
     * @return
     */
    public static String joinCollectionToString(Collection<String> collection) {
        StringBuffer stringBuffer = new StringBuffer();
        if (collection.size() > 0) {
            Iterator<String> iterator = collection.iterator();
            while (iterator.hasNext()) {
                String item = iterator.next();
                stringBuffer.append(item);
                if (iterator.hasNext()) {
                    stringBuffer.append("&");
                }
            }
        }
        return stringBuffer.toString();
    }

    public static Set<String> toLinkedSet(Map<String, String> map, String joinStr) {
        if (map.size() > 0) {
            Set<String> param = new LinkedHashSet<String>();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet()
                    .iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> ent = iterator.next();
                param.add(ent.getKey() + joinStr + ent.getValue());
            }
            return param;
        }
        return null;
    }

    public static String sign(Map<String, String> map, String secretKey, String enc) {
        try {
            String needSignString = joinCollectionToString(toSortSet(map, "="));
            String sign = null;
            if (null == secretKey || "".equals(secretKey)) {
                sign = EncryptHelper.md5(needSignString, enc);
                logger.info("加密前字符串为:{}", needSignString);
            } else {
                String preSign = needSignString + "&secret_key=" + secretKey;
                logger.info("加密前字符串为:{}", preSign);
                sign = EncryptHelper.md5(preSign, enc);
            }
            return sign.toUpperCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, String> param = new HashMap<>();
        param.put("api_key","few");
        param.put("symbol","f9d9sag8fdsa");
        param.put("type","sell");
        param.put("price",String.valueOf("43.43"));
        param.put("amount",String.valueOf("543.5"));
        param.put("api_key", "fdsfod00-s");
        System.out.println(toSortSet(param,"="));
        String sign = EncryptHelper.sign(param, "f9ds90fds90", "utf-8");
        System.out.println(sign);
    }
}