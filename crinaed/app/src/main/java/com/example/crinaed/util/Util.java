package com.example.crinaed.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.crinaed.R;

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
    private static Util instance = new Util();
    private String sessionId="";
    private long idUser=-1;

    public String getSessionId(){
        return sessionId;
    }

    public long getIdUser(){
        return idUser;
    }

    public static Util getInstance() {
        return Util.instance;
    }

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
                Log.d("error", string);
            }
            e.printStackTrace();
            return 0;
        }

    }

    public static String timestampToFormat(long l, String s){
        SimpleDateFormat dateFormat = new SimpleDateFormat(s, Locale.ITALY);
        return dateFormat.format(new Date(l));
    }

    public static String timestampToIso(long l){
        return timestampToIso(l, Locale.ITALY);
    }
    public static String timestampToIso(long lo, Locale l){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", l);
        return dateFormat.format(new Date(lo));
    }

    public static String dateToTimestamp(Date l){
        return dateToTimestamp(l, Locale.ITALY);
    }

    public static String dateToTimestamp(Date l, Locale lo){
        return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", lo)).format(l);
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

    public boolean checkData(Context context){
        /* TODO maybe is wrong */
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.sessionId), Context.MODE_PRIVATE);
        Log.d("naed", "checkData "+preferences.contains("value"));
        return preferences.contains("value");
    }

    public void setSessionId(String sessionId){
        this.sessionId=sessionId;
    }
    public void setIdUser(long idUser){
        this.idUser=idUser;
    }

}
