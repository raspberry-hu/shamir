package com.share.shamir.util.shamir;

import com.codahale.shamir.Scheme;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
//        System.out.println(Charset.defaultCharset().name());
//        byte[] a = parts.get(1);
//        System.out.println(Arrays.toString(a));
//        String aStr = new String(a, Charset.forName("ISO-8859-1"));
//        byte[] b = aStr.getBytes(Charset.forName("ISO-8859-1"));
//        System.out.println(Arrays.toString(b));
//        String aStr = new String(parts.get(1), Charset.forName("ISO-8859-1"));
//        System.out.println("打印1" + parts.get(1));
//        System.out.println("打印1" + aStr);
//        System.out.println("打印1" + aStr.getBytes(Charset.forName("ISO-8859-1")));
//        System.out.println("打印1" + parts.get(1));
//        System.out.println("打印1" + parts.get(1).length);
//        System.out.println("打印1" + parts.get(1)[0]);
//        System.out.println("打印1" + parts.get(1)[1]);
//        System.out.println("打印1" + parts.get(1)[2]);
//        System.out.println("打印" + temp + "长度" + temp.size() + "密钥" + secret);
//        System.out.println("打印" + parts + "长度" + parts.size() + "密钥" + secret);
//        System.out.println(ShamirUtils.shamirRecover(temp));
        return temp;
    }

    public static String shamirRecover(HashMap<Integer, byte[]> parts) {
        final Scheme scheme = new Scheme(new SecureRandom(), parts.size(), parts.size() - 1);
        final byte[] recovered = scheme.join(parts);
//        System.out.println("打印" + recovered);
//        System.out.println("打印" + new String(recovered, StandardCharsets.UTF_8));
        return new String(recovered, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        HashMap<Integer, byte[]> parts = shamirGenerate("vvtest123", 5, 3);
        System.out.println(parts);
        System.out.println(shamirRecover(parts));
    }
}
