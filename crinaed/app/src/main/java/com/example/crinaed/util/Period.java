package com.example.crinaed.util;

import java.util.Calendar;
import java.util.Date;

public enum Period {
    DAY(1), WEEK(7), MONTH(30), YEAR(365), EVER(3650);

    private int day;

    public int getDay() {
        return this.day;
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

    Period(int day) {
        this.day = day;
    }


}
