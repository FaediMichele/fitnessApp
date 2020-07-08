package com.example.crinaed.ProgressBar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.database.repository.CommitmentRepository;
import com.example.crinaed.util.Category;
import com.example.crinaed.util.Pair;
import com.example.crinaed.util.Period;
import com.example.crinaed.view.GraphUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class GraphAdapter extends RecyclerView.Adapter<GraphAdapter.GraphAdapterVH> {
    List<CommitmentWithMyStep> newest;
    Category category;
    LifecycleOwner owner;

    public GraphAdapter(LifecycleOwner owner, Category category){
        this.category=category;
        this.owner=owner;
        DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().getCommitmentWithSteps(category).observe(owner, new Observer<List<CommitmentWithMyStep>>() {
            @Override
            public void onChanged(List<CommitmentWithMyStep> commitmentWithMySteps) {
                newest=commitmentWithMySteps;
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public GraphAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_graph_objective, parent,false);
        return new GraphAdapterVH(inflate, category, owner);
    }

    @Override
    public void onBindViewHolder(@NonNull GraphAdapterVH holder, int position) {
        holder.setData(newest.get(position));
    }

    @Override
    public int getItemCount() {
        if(newest!=null){
            return  newest.size();
        }
        return 0;
    }

    static class GraphAdapterVH extends RecyclerView.ViewHolder{
        TextView name;
        Button week;
        Button month;
        Button year;
        LineChart chart;
        View itemView;
        Context context;

        Period period=Period.WEEK;
        Category category;
        List<MyStepDoneWithMyStep> newestDay;
        List<MyStepDoneWithMyStep> newestWeek;
        List<MyStepDoneWithMyStep> newestBoth=new ArrayList<>();
        LiveData<List<MyStepDoneWithMyStep>> historyDay = null;
        LiveData<List<MyStepDoneWithMyStep>> historyWeek = null;
        CommitmentRepository repo = DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository();
        LifecycleOwner owner;
        CommitmentWithMyStep data;

        final Observer<List<MyStepDoneWithMyStep>> historyObserverDay = new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                newestDay=steps;
                setLine();
                //Log.d("naed", category.name() + "-> updated the data: " + steps.toString());
            }
        };
        final Observer<List<MyStepDoneWithMyStep>> historyObserverWeek = new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                newestWeek=steps;
                setLine();
            }
        };

        public GraphAdapterVH(@NonNull View itemView, Category category, LifecycleOwner owner) {
            super(itemView);
            name=itemView.findViewById(R.id.name_objective);
            week=itemView.findViewById(R.id.button_week);
            month=itemView.findViewById(R.id.button_month);
            year=itemView.findViewById(R.id.button_year);
            chart=itemView.findViewById(R.id.lineChart);
            this.itemView=itemView;
            context=itemView.getContext();
            this.category=category;
            this.owner=owner;
            week.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    period=Period.WEEK;
                    update();
                }
            });
            month.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    period=Period.MONTH;
                    update();
                }
            });
            year.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    period=Period.YEAR;
                    update();
                }
            });
        }

        private void update(){
            updateObserver();
            setLine();
        }

        private void setData(CommitmentWithMyStep data){
            this.data=data;
            name.setText(data.commitment.name);
            historyDay = repo
                    .getStepHistoryForCommitment(data.commitment.idCommitment, category, period.daysAgo(), Period.DAY);
            historyWeek = repo
                    .getStepHistoryForCommitment(data.commitment.idCommitment, category, period.daysAgo(), Period.WEEK);
            update();
        }

        private void updateObserver(){
            if(historyDay != null){
                historyDay.removeObserver(historyObserverDay);
            }
            historyDay = repo.getStepHistoryForCommitment(data.commitment.idCommitment, category, period.daysAgo(), Period.DAY);
            historyDay.observe(owner, historyObserverDay);
            if(historyWeek != null){
                historyWeek.removeObserver(historyObserverDay);
            }
            historyWeek = repo.getStepHistoryForCommitment(data.commitment.idCommitment, category, period.daysAgo(), Period.WEEK);
            historyWeek.observe(owner, historyObserverWeek);
        }

        private void setLine(){
            newestBoth.clear();
            if(newestDay!=null){
                newestBoth.addAll(newestDay);
            }
            if(newestWeek!=null){
                newestBoth.addAll(newestWeek);
            }
            //Log.d("naed", category.name() + " -> newest both: " + newestBoth);
            Pair<List<Entry>, IAxisValueFormatter> entries = GraphUtil.getGraphData(data.commitment, newestBoth, period);
            LineData lineData = new LineData();
            setLineDataSet(lineData, new LineDataSet(entries.getX(), context.getString(category.getRes())), category);
            chart.getXAxis().setValueFormatter(entries.getY());
            chart.setData(lineData);
            chart.animateY(300);
            chart.animate();
            chart.setMinimumHeight(200);
            Description d= new Description();
            d.setTextColor(Color.argb(0,0,0,0)); // hide description
            chart.setDescription(d);
            chart.setVisibleYRange(0,120, YAxis.AxisDependency.LEFT);
            chart.setScaleXEnabled(false);
            chart.getAxisLeft().setTextColor(Color.argb(0,0,0,0));
            chart.getAxisRight().setTextColor(Color.argb(0,0,0,0));
            chart. setGridBackgroundColor(Color.argb(0,0,0,0));
            chart.getXAxis().setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            chart.getXAxis().setDrawGridLines(false);
            chart.getAxisRight().setDrawGridLines(false);
            chart.getAxisLeft().setDrawGridLines(false);
            chart.getXAxis().setTextSize(15);
            chart.setDrawBorders(false);
        }
        private void setLineDataSet(LineData lineData, LineDataSet lineDataSet, Category c){
            lineDataSet.setColor(c.toColor(context).getX());
            lineDataSet.setCubicIntensity(0.5f);
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineData.addDataSet(lineDataSet);
            lineDataSet.setValueTextColor(Color.argb(0,0,0,0));  // invisible value
            lineDataSet.setCircleRadius(lineDataSet.getLineWidth());                    // invisible circle
            lineDataSet.setCircleColor(c.toColor(context).getY());                      // invisible circle
            lineDataSet.setLabel("");                                                   // hide legend text
            lineDataSet.setForm(Legend.LegendForm.NONE);                                // hide legend color
        }
    }
}
