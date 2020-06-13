package com.example.crinaed.util;


import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static long isoFormatToTimestamp(String string){
        return isoFormatToTimestamp(string, Locale.ITALY);
    }
    public static long isoFormatToTimestamp(String string, Locale l){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ITALY);
        try {
            return dateFormat.parse(string).getTime();
        } catch (Exception e) {
            try{
                dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ITALY);
                return dateFormat.parse(string).getTime();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            e.printStackTrace();
            return 0;
        }

    }

    public static String timestampToIso(long l){
        return timestampToIso(l, Locale.ITALY);
    }
    public static String timestampToIso(long lo, Locale l){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", l);
        return dateFormat.format(new Date(lo));
    }


    public static String hash(String s){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(s.getBytes());
            byte[] sha=md.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte bytes : sha) {
                stringBuilder.append(String.format("%02x", bytes & 0xff));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }
}
