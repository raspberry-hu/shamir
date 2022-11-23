package com.share.shamir.util.shamir;

import com.codahale.shamir.Scheme;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShamirUtils {
    public static HashMap shamirGenerate(String secret, int n, int k) {
        final Scheme scheme = new Scheme(new SecureRandom(), n, k);
        final byte[] secretByte = secret.getBytes(StandardCharsets.UTF_8);
        final Map<Integer, byte[]> parts = scheme.split(secretByte);
        HashMap<Integer, byte[]> temp = new HashMap<>();
        temp.putAll(parts);
        return temp;
    }

    public static String shamirRecover(HashMap<Integer, byte[]> parts) {
        final Scheme scheme = new Scheme(new SecureRandom(), parts.size(), parts.size() - 1);
        final byte[] recovered = scheme.join(parts);
        return new String(recovered, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        HashMap<Integer, byte[]> parts = shamirGenerate("vvtest123", 5, 3);
        System.out.println(parts);
        System.out.println(shamirRecover(parts));
    }

    public static String getSHA256StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
