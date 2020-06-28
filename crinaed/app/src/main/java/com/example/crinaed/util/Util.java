package com.example.crinaed.util;


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
        } catch (ParseException e) {
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
}
