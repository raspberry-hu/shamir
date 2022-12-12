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
import java.util.Iterator;
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

    public static void main(String[] args) throws UnsupportedEncodingException {
        HashMap<Integer, byte[]> parts = shamirGenerate("china", 5, 4);
        Iterator<Map.Entry<Integer, byte[]>> iterator = parts.entrySet().iterator();
        HashMap<Integer, byte[]> temp = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            Map.Entry<Integer, byte[]> entry = iterator.next();
            //获取用户信息
            //获取用户密钥分配信息
            byte[] a = entry.getValue();
            System.out.println(Arrays.toString(a));
//            System.out.println(a);
//            String aStr = new String(a, "ISO-8859-1");
//            String aStr = new String(a, "utf-8");
//            System.out.println(aStr);
            String[] end = new String[a.length];
            String temp1 = Arrays.toString(a);
            temp1 = temp1.substring(1,temp1.length()-1);
//            System.out.println(temp1);
            temp1 = temp1.replaceAll(" ", "");
            System.out.println(temp1);
            String delimeter = ",";
            end = temp1.split(delimeter);
//            System.out.println(end);

            byte b[];
            b = new byte[end.length];
            for (int j = 0;j < end.length;j++) {
                b[j] = (byte) Integer.parseInt(end[j]);
            }
            System.out.println(Arrays.toString(b));
            temp.put(i+1, a);

//            System.out.println(aStr.getBytes("ISO-8859-1"));
//            temp.put(i+1, aStr.getBytes("utf-8"));
//            System.out.println(Arrays.toString(aStr.getBytes("utf-8")));
        }

        System.out.println(parts);
        System.out.println(temp);
//        System.out.println(shamirRecover(parts));
//        System.out.println(shamirRecover(temp));
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
