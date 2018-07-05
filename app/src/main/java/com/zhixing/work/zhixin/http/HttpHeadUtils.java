package com.zhixing.work.zhixin.http;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 辅助添加Head类
 */

public class HttpHeadUtils {
    public static final String KEY_ACCESSID = "accessId";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_NONCE = "nonce";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_SIGNATURE = "signature";

    public static String ACCESS_ID = "7568512625";
    public static String ACCESS_SECRET = "a02f3025-9c9b-4737-897d-7e94242797a0";

    //获取时间戳
    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Calendar calendar = Calendar.getInstance();
        int offset = calendar.get(Calendar.ZONE_OFFSET);
        calendar.add(Calendar.MILLISECOND, -offset);
        Date date = calendar.getTime();
        String timer = sdf.format(date);
        return timer;
    }

    //获取随机数
    public static String getRandom() {
        float random = new Random().nextFloat();
        String randoms = String.valueOf(random);
        return randoms;

    }

    public static String getASCII(String accessId, String time, String random, String accessSecret) {
        String[] tempArr = {accessId, time, random, accessSecret};
        Arrays.sort(tempArr);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tempArr.length; i++) {
            sb.append(tempArr[i]);
        }
        return sb.toString();
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    //获取签名串
    public static String getSignature(String time, String random) {
        String ASCII = getASCII(ACCESS_ID,time, random, ACCESS_SECRET);
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(HttpHeadUtils.ACCESS_SECRET.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(ASCII.getBytes());
            hash = byteArrayToHexString(bytes);
            hash = hash.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return hash;
    }






}
