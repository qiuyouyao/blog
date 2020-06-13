package com.tutu.blog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * @author tutu 2020/4/30 23:12
 */
public class Md5Util {

    private static final String MD5 = "MD5";

    /**
     * 将指定byte数组转换成16进制字符串
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes){
        try {
            MessageDigest md5 = MessageDigest.getInstance(MD5);
            md5.update(bytes);
            byte[] hash = md5.digest();
            StringBuilder secPwd = new StringBuilder();
            for (byte b : hash) {
                int value = b & 0xff;
                if (value < 16) {
                    secPwd.append(0);
                }
                secPwd.append(Integer.toString(value, 16));
            }
            return secPwd.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
