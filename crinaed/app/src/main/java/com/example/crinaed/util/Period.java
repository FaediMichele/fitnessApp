package com.example.crinaed.util;

import android.content.Context;

import com.example.crinaed.R;

import java.util.Calendar;
import java.util.Date;

public enum Period {
    DAY(1, R.string.day), WEEK(7, R.string.week), MONTH(30, R.string.month), YEAR(365, R.string.year), EVER(3650, R.string.ever);

    private int day;
    private int resId;

    public int getDay() {
        return this.day;
    }

    public int getResId(){
        return resId;
    }

    Period(int day, int resId) {
        this.day = day;
        this.resId=resId;
    }

    public Date daysAgo(){
        Date ret = new Date();
        return decrementDays(ret, getDay()-1);
    }

    private static Date decrementDays(Date d, int days){
        Calendar cal= Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }

    public static String[] toRepetition(Context context){
        return new String[]{context.getString(Period.DAY.getResId()), context.getString(Period.WEEK.getResId())};
    }

    public static Period getRepetition(int position){
        return position==0 ? Period.DAY: Period.WEEK;
    }

    public static Period fromDays(int days){
        Period[] val = values();
        for(int i=0;i<val.length;i++){
            if(val[i].getDay()==days){
                return val[i];
            }
        }
        return null;
    }
}
