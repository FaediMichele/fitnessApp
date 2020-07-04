package com.example.crinaed.view;

import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.util.Period;
import com.example.crinaed.util.Util;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GraphUtil {

    private GraphUtil(){
    }

    // Period should not be too big. It will cause delay
    public static List<Entry> getGraphData(List<MyStepDoneWithMyStep> data, Period t){
        List<Entry> ret = new ArrayList<>();

        for(int i =0; i< t.getDay();i++){
            Calendar c = GregorianCalendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -i-1);

            ret.add(new Entry((i+1) , 0)); // Util.timestampToIsoMonth(c.getTime().getTime(), Locale.ITALY)));
        }
        if(data.size()==0){
            return ret;
        }

        Date now = new Date();

        Float[] sum= new Float[t.getDay()];
        Integer[] count= new Integer[t.getDay()];

        for(int i=0; i<data.size(); i++){
            float f= (float) Math.floor(100*(data.get(i).stepDone.result/data.get(i).step.max));
            int index = t.getDay() - (int) TimeUnit.MILLISECONDS.toDays(now.getTime() - data.get(i).stepDone.dateStart);
            if(index >= sum.length || index<0){
                continue;
            }
            if(sum[index]==null){
                sum[index]=f;
                count[index]=1;
            } else {
                sum[index] = sum[index] + f;
                count[index] = count[index] + 1;
            }
        }

        for(int i=0; i < data.size(); i++){
            int index = t.getDay() - (int) TimeUnit.MILLISECONDS.toDays(now.getTime() - data.get(i).stepDone.dateStart) ;
            if(index >= sum.length || index<0){
                continue;
            }
            ret.get(index).setY(sum[index]/count[index]);
        }

        return ret;
    }
}
