package com.example.crinaed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.williamchart.data.DataPoint;
import com.db.williamchart.view.LineChartView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ChartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        LineChartView line = view.findViewById(R.id.lineChart);
        line.animate(generateData());
        return view;
    }



    private LinkedHashMap<String, Float> generateData(){
        LinkedHashMap<String, Float> ret = new LinkedHashMap<>();
        ret.put("lbl1", 5f);
        ret.put("lbl2", 4f);
        ret.put("lbl3", 2f);
        ret.put("lbl4", 4f);
        ret.put("lbl5", 1f);
        ret.put("lbl6", 1.7f);

        return ret;
    }
}
