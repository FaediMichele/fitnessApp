package com.example.crinaed.ProgressBar;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.crinaed.layout.home.DetailsProgressBarDialog;
import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.database.repository.CommitmentRepository;
import com.example.crinaed.util.Category;
import com.example.crinaed.util.Period;
import com.example.crinaed.util.Single;
import com.example.crinaed.view.GraphUtil;
import com.example.crinaed.view.ProgressBarView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * This class manage the categories and the relatives Dialog.
 */
public class SliderProgressBarAdapter extends SliderViewAdapter<SliderProgressBarAdapter.SliderProgressBarVH> {

    public final static String TAG = "LAUNCH_DETAIL_FRAGMENT";
    private Context context;
    private LifecycleOwner owner;
    private Single<Period> period = new Single<>(Period.WEEK);
    private List<SliderProgressBarVH> holders = new ArrayList<>();

    public SliderProgressBarAdapter(Context context, LifecycleOwner owner) {
        CommitmentRepository repo = DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository();
        this.owner=owner;
        this.context = context;
        repo.updateMyStepDone();
    }

    public void setPeriod(Period period){
        if (this.period.getVal() != period) {
            this.period.setVal(period);
            updateGraph();
        }
    }
    private void updateGraph(){
        for(SliderProgressBarVH holder : holders){
            holder.update();
        }
    }

    public Category getCategoryForPosition(int position){
        return Category.values()[position];
    }

    @Override
    public SliderProgressBarVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar_item, null);
        holders.add(new SliderProgressBarVH(inflate, context, owner, period));
        return holders.get(holders.size()-1);
    }

    @Override
    public void onBindViewHolder(final SliderProgressBarVH viewHolder, final int position) {
        viewHolder.setCategory(getCategoryForPosition(position));
        int primaryColor;
        int secondaryColor;

        switch(Category.values()[position]){
            case SOCIAL:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.greenPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.greenSecondary);
                break;
            case MENTAL:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.bluPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.bluSecondary);
                break;
            case SPORT:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.redPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.redSecondary);
                break;
            default:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.redPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.redSecondary);
                break;
        }
        viewHolder.progressBarView.setForegroundColor(primaryColor);
        viewHolder.progressBarView.setBackgroundColor(secondaryColor);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Float progress = viewHolder.progressBarView.getProgress();
                // progress = (progress+10)%100;
                // viewHolder.progressBarView.setProgress(progress);
                //build data for dialog
                if(!viewHolder.isDialog()){
                    DetailsProgressBarDialog dialog = new DetailsProgressBarDialog(context, context.getString(getCategoryForPosition(position).getRes()), getCategoryForPosition(position), owner);
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            viewHolder.setDialogStatus(false);
                        }
                    });
                    viewHolder.setDialogStatus(true);
                    dialog.show();
                }
            }
        });
    }

    private Float getPercentProgress (List<MyStepDoneWithMyStep> steps){
        if(steps.size()==0){
            return 1f;
        }
        Double result = 0.0;
        for (MyStepDoneWithMyStep step: steps) {
            result = result + ((double) step.stepDone.result)/step.step.max;
        }
        result = result / steps.size();
        return result.floatValue()*100;
    }

    @Override
    public int getCount() {
        return Category.values().length;
    }

    static class SliderProgressBarVH extends SliderViewAdapter.ViewHolder {
        final Single<Boolean> first = new Single<>(true);
        View itemView;
        ProgressBarView progressBarView;
        Switch switch_repetition;
        LineChart chart;
        Single<Period> period;

        CommitmentRepository repo = DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository();
        Category category;
        Context context;
        LifecycleOwner owner;
        List<MyStepDoneWithMyStep> newest;
        Single<Period> repetition;
        LiveData<List<MyStepDoneWithMyStep>> onGoing = null;
        LiveData<List<MyStepDoneWithMyStep>> history = null;
        private boolean status = false;


        final Observer<List<MyStepDoneWithMyStep>> onGoingObserver = new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                if(steps.size() == 0){
                    progressBarView.setProgress(0);
                } else {
                    float sum = 0;
                    float weights = 0;
                    for (MyStepDoneWithMyStep step : steps) {
                        sum += step.stepDone.result;
                        weights += step.step.max;
                    }
                    progressBarView.setProgress(100*sum / weights);
                }
            }
        };

        final Observer<List<MyStepDoneWithMyStep>> historyObserver = new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                newest=steps;
                setLine(steps);
            }
        };

        public SliderProgressBarVH(View itemView, final Context context, LifecycleOwner owner, Single<Period> period) {
            super(itemView);
            chart= itemView.findViewById(R.id.lineChart);
            progressBarView = itemView.findViewById(R.id.progressBarItem);

            repetition = new Single<>(Period.DAY);
            switch_repetition = itemView.findViewById(R.id.switch_repetition);
            switch_repetition.setText(context.getString(repetition.getVal().getResId()));
            switch_repetition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(repetition.getVal() == Period.DAY){
                        repetition.setVal(Period.WEEK);
                    } else {
                        repetition.setVal(Period.DAY);
                    }
                    switch_repetition.setText(context.getString(repetition.getVal().getResId()));
                    updateObserver();
                    update();
                }
            });
            this.itemView = itemView;
            this.context=context;
            this.owner=owner;

            this.period=period;
        }

        public void setDialogStatus(boolean status){
            this.status=status;
        }
        public boolean isDialog(){
            return this.status;
        }


        private void updateObserver(){
            if(history != null){
                history.removeObserver(historyObserver);
            }
            history = repo.getStepHistory(category, period.getVal().daysAgo(), repetition.getVal());
            history.observe(owner, historyObserver);

            if(onGoing != null){
                onGoing.removeObserver(onGoingObserver);
            }
            onGoing = repo.getStepOnGoing(category, repetition.getVal());
            onGoing.observe(owner, onGoingObserver);
        }

        public void update(){
            setLine(newest);
        }

        private void setCategory(final Category category){
            if(first.getVal()){
                this.category=category;
                first.setVal(false);
                updateObserver();
            }
        }

        private void setLine(List<MyStepDoneWithMyStep> data){
            List<Entry> entries = GraphUtil.getGraphData(data, period.getVal());
            LineData lineData = new LineData();
            setLineDataSet(lineData, new LineDataSet(entries, context.getString(category.getRes())), category);
            chart.setData(lineData);
            chart.animateY(300);
            chart.animate();
            chart.setMinimumHeight(200);
            Description d= new Description();
            d.setTextColor(Color.argb(0,0,0,0)); // hide description
            chart.setDescription(d);
            chart.setVisibleYRange(0,120, YAxis.AxisDependency.LEFT);
            chart.setScaleXEnabled(false);
            chart.getAxisLeft().setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            chart.getAxisRight().setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            chart.getXAxis().setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
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
