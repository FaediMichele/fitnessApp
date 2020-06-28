package com.example.crinaed.view;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.database.repository.CommitmentRepository;
import com.example.crinaed.util.Category;
import com.example.crinaed.util.Period;
import com.example.crinaed.util.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GraphUtil {

    private GraphUtil(){
    }

    public static LinkedHashMap<String, Float> getGraphData(Category c, Period t){
        CommitmentRepository repo = DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository();
        List<MyStepDoneWithMyStep> data = repo.getStepHistory(c, periodToDate(t));
        LinkedHashMap<String, Float> ret = new LinkedHashMap<>();

        if(data.size()==1){
            int dayToCount = t==Period.EVER? 5: t.getDay();
            ret.put("Start", 100f);
            Date d= Util.timestampToDate(data.get(0).stepDone.dateStart) ;
            d= decrementDays(d, dayToCount);
            for(int i=0; i < dayToCount && d.getTime()<data.get(0).stepDone.dateStart; i++){

                ret.put(Util.timestampToFormat(d.getTime(), "MM/dd"), 0f);
                d=decrementDays(d, -i);
            }
        }
        for(int i=0; i<data.size(); i++){
            float f= (float) Math.floor(100*(data.get(i).stepDone.result/data.get(i).step.max));
            Log.d("naed", "float: " +f);
            // Log.d("naed", "graph: " + Util.timestampToIso(data.get(i).stepDone.dateStart) + " || " +data.get(i).step.name + " || " + (float) (data.get(i).stepDone.result/data.get(i).step.max));
            ret.put(Util.timestampToFormat(data.get(i).stepDone.dateStart, "MM/dd"), f);
        }

        return ret;
    }


    private static Date periodToDate(Period p){
        Date ret = new Date();
        return decrementDays(ret, p.getDay());
    }

    private static Date decrementDays(Date d, int days){
        Calendar cal= Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }
}
