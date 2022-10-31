package com.example.crinaed.util;

import android.content.Context;
import android.content.res.Resources;

import androidx.core.content.ContextCompat;

import com.example.crinaed.R;

public enum Category{
    SPORT(R.color.redPrimary, R.color.redSecondary, R.string.sport), MENTAL(R.color.bluPrimary, R.color.bluSecondary, R.string.mental), SOCIAL(R.color.greenPrimary, R.color.greenSecondary, R.string.social);

    int primary;
    int secondary;
    int nameRes;
    Category(int primaryColorRes, int secondaryColorRes, int nameRes){
        primary=primaryColorRes;
        secondary=secondaryColorRes;
        this.nameRes=nameRes;
    }

    public Pair<Integer,Integer> toColor(Context c){
        return new Pair<>(ContextCompat.getColor(c, primary),ContextCompat.getColor(c, secondary));
    }

    public static String[] toLocalized(Context context){
        Category[] array= Category.values();
        String[] ret = new String[array.length];
        for(int i=0;i<array.length;i++){
            ret[i]=context.getString(array[i].getRes());
        }
        return ret;
    }

    public int getRes(){
        return nameRes;
    }
}