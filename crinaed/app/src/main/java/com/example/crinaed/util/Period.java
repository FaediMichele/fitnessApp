package com.example.crinaed.util;

public enum Period {
    DAY(1), WEEK(7), MONTH(30), YEAR(365), EVER(36500);

    private int day;

    public int getDay() {
        return this.day;
    }

    Period(int day) {
        this.day = day;
    }
}
