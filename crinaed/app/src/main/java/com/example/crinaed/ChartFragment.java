package com.example.crinaed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.williamchart.data.DataPoint;
import com.db.williamchart.view.LineChartView;
import com.example.crinaed.util.Category;
import com.example.crinaed.util.Period;
import com.example.crinaed.view.GraphUtil;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class ChartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        LineChartView lineSocial = view.findViewById(R.id.lineChart_social);
        LinkedHashMap<String, Float> social =GraphUtil.getGraphData(Category.SOCIAL, Period.WEEK);
        Log.d("naed", "social: " + social.toString());
        lineSocial.animate(GraphUtil.getGraphData(Category.SOCIAL, Period.WEEK));
        lineSocial.setTop(100);


        LineChartView lineSport = view.findViewById(R.id.lineChart_sport);
        lineSport.animate(GraphUtil.getGraphData(Category.SPORT, Period.EVER));
        lineSport.setTop(100);

        LineChartView lineMental = view.findViewById(R.id.lineChart_mental);
        lineMental.animate(GraphUtil.getGraphData(Category.MENTAL, Period.EVER));
        lineMental.setTop(100);

        return view;
    }




}
