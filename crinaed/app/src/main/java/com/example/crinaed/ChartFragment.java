package com.example.crinaed;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.github.mikephil.charting.charts.LineChart;
import com.example.crinaed.util.Category;
import com.example.crinaed.util.Period;
import com.example.crinaed.view.GraphUtil;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class ChartFragment extends Fragment {
    private LineChart social;
    private LineChart sport;
    private LineChart mental;

    private Period repetition;
    private Period period;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        social = view.findViewById(R.id.lineChart_social);
        sport = view.findViewById(R.id.lineChart_sport);
        mental = view.findViewById(R.id.lineChart_mental);


        final Switch day = view.findViewById(R.id.switch_day);
        final Switch week = view.findViewById(R.id.switch_week);
        final Switch year = view.findViewById(R.id.switch_year);
        final Switch month = view.findViewById(R.id.switch_month);


        final Switch repetition_day = view.findViewById(R.id.repetition_day);
        final Switch repetition_week = view.findViewById(R.id.repetition_week);

        repetition_day.setChecked(true);
        day.setChecked(true);
        repetition=Period.DAY;
        period=Period.WEEK;

        updatePeriod();

        repetition_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!repetition_week.isChecked()){
                    repetition_day.setChecked(true);
                    repetition=Period.DAY;
                    updatePeriod();
                    return;
                }
                repetition_week.setChecked(false);
                repetition=Period.DAY;
                updatePeriod();
            }
        });

        repetition_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!repetition_day.isChecked()){
                    repetition_week.setChecked(true);
                    repetition=Period.WEEK;
                    updatePeriod();
                    return;
                }
                repetition_day.setChecked(false);
                repetition=Period.WEEK;
                updatePeriod();
            }
        });

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                week.setChecked(false);
                year.setChecked(false);
                month.setChecked(false);
                period=Period.DAY;
                updatePeriod();
            }
        });

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day.setChecked(false);
                year.setChecked(false);
                month.setChecked(false);
                period=Period.WEEK;
                updatePeriod();
            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                week.setChecked(false);
                day.setChecked(false);
                month.setChecked(false);
                period=Period.YEAR;
                updatePeriod();
            }
        });
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                week.setChecked(false);
                year.setChecked(false);
                day.setChecked(false);
                period=Period.MONTH;
                updatePeriod();
            }
        });


        return view;
    }

    private void updatePeriod(){
        setLine(social, Category.SOCIAL, period, getString(R.string.social));
        setLine(sport, Category.SPORT, period, getString(R.string.sport));
        setLine(mental, Category.MENTAL, period, getString(R.string.mental));
    }

    private void setLine(LineChart line, Category c, Period t, String label){
        List<Entry> entries =GraphUtil.getGraphData(c, t, repetition);
        LineData lineData = new LineData();
        setLineDataSet(lineData, new LineDataSet(entries, label));
        line.setData(lineData);
        line.animateXY(1000,1000);
        line.animate();
        line.setMinimumHeight(200);
        Description d= new Description();
        d.setTextColor(Color.argb(0,0,0,0)); // hide description
        line.setDescription(d);
        line.setVisibleYRange(0,120, YAxis.AxisDependency.LEFT);
    }

    private void setLineDataSet(LineData lineData, LineDataSet lineDataSet){
        lineDataSet.setColor(Color.rgb(255, 0, 100));
        lineDataSet.setCubicIntensity(0.5f);
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineData.addDataSet(lineDataSet);
        lineDataSet.setValueTextColor(Color.argb(0,0,0,0)); // invisible value
        lineDataSet.setCircleRadius(lineDataSet.getLineWidth()/2); // invisible circle
        lineDataSet.setCircleColor(lineDataSet.getColor());        // invisible circle
        lineDataSet.setLabel("");                                  // hide legend text
        lineDataSet.setForm(Legend.LegendForm.NONE);               // hide legend color
    }




}
