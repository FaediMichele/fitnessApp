package com.example.crinaed.util;

import android.content.Context;

import com.example.crinaed.R;

public enum TypeOfStep{
    CHECKLIST(R.string.checklist, "checklist"), PROGRESSION(R.string.progression, "progression");
    int resId;
    String type;
    TypeOfStep(int resId, String type){
        this.resId=resId;
        this.type=type;
    }

    public int getResId() {
        return resId;
    }

    public String getType() {
        return type;
    }

    public static String[] toLocalized(Context context){
        TypeOfStep[] array= TypeOfStep.values();
        String[] ret = new String[array.length];
        for(int i=0;i<array.length;i++){
            ret[i]=context.getString(array[i].getResId());
        }
        return ret;
    }
}