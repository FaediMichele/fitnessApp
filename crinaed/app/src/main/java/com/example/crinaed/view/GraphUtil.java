package com.example.crinaed.view;

import android.graphics.Typeface;
import android.util.Log;

import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.util.Pair;
import com.example.crinaed.util.Period;
import com.example.crinaed.util.Util;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

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
    private static IAxisValueFormatter getFormatter(final Period t){
        return new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //axis.setTypeface(Typeface.MONOSPACE);
                axis.setYOffset(-1);
                Calendar c = GregorianCalendar.getInstance();
                c.add(Calendar.DAY_OF_YEAR, (int) -Math.ceil(t.getDay() - value)-1);
                axis.setLabelCount(t== Period.YEAR?6:t==Period.MONTH?4:7, true);
                return Util.timestampToIsoMonth(c.getTime().getTime());
            }
        };
    }

    public static Pair<List<Entry>, IAxisValueFormatter> getGraphData(MyCommitment commitment, List<MyStepDoneWithMyStep> data, Period t){
        List<Entry> ret = new ArrayList<>();
        int startIndex = Math.max((int)(t.getDay() - (new Date().getTime() - commitment.creationDate)/(1000*24*60*60)), 0);
        for(int i = 0; i< t.getDay(); i++){
            ret.add(new Entry((i+1) , 0)); // Util.timestampToIsoMonth(c.getTime().getTime(), Locale.ITALY)));
        }
        if(data.size()==0){
            return new Pair<>(ret, getFormatter(t));
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
        if(startIndex == ret.size()){
            return new Pair<>(ret.subList(startIndex-1, ret.size()), getFormatter(t));
        }
        return new Pair<>(ret.subList(startIndex, ret.size()), getFormatter(t));
    }

    // Period should not be too big. It will cause delay
    public static Pair<List<Entry>, IAxisValueFormatter> getGraphData(List<MyStepDoneWithMyStep> data, Period t){
        List<Entry> ret = new ArrayList<>();

        for(int i =0; i< t.getDay();i++){
            // Calendar c = GregorianCalendar.getInstance();
            // c.add(Calendar.DAY_OF_MONTH, -i-1);

            ret.add(new Entry((i+1) , 0)); // Util.timestampToIsoMonth(c.getTime().getTime(), Locale.ITALY)));
        }
        if(data.size()==0){
            return new Pair<>(ret, getFormatter(t));
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

        return new Pair<>(ret, getFormatter(t));
    }
}
