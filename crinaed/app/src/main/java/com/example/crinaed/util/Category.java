package com.example.crinaed.util;

import android.content.Context;
import android.content.res.Resources;

import androidx.core.content.ContextCompat;

import com.example.crinaed.R;

public enum Category{
    SPORT(R.color.redPrimary, R.color.redSecondary), MENTAL(R.color.bluPrimary, R.color.bluSecondary), SOCIAL(R.color.greenPrimary, R.color.greenSecondary);

    int primary;
    int secondary;
    Category(int primaryColorRes, int secondaryColorRes){
        primary=primaryColorRes;
        secondary=secondaryColorRes;
    }

    public Pair<Integer,Integer> toColor(Context c){
        return new Pair<>(ContextCompat.getColor(c, primary),ContextCompat.getColor(c, secondary));
    }
}