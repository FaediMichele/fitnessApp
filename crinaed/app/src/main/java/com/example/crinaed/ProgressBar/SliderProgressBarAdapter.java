package com.example.crinaed.ProgressBar;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.database.ServerManager;
import com.example.crinaed.layout.home.DetailsProgressBarDialog;
import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.database.repository.CommitmentRepository;
import com.example.crinaed.layout.home.HomeActivity;
import com.example.crinaed.layout.home.login.LoginFragment;
import com.example.crinaed.util.Category;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Pair;
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.SliderViewAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * This class manage the categories and the relatives Dialog.
 */
public class SliderProgressBarAdapter extends SliderViewAdapter<SliderProgressBarAdapter.SliderProgressBarVH> {
    private Context context;
    private LifecycleOwner owner;
    private SliderProgressBarVH[] created = new SliderProgressBarVH[Category.values().length];
    private FragmentActivity activity;

    public SliderProgressBarAdapter(Context context, LifecycleOwner owner, FragmentActivity activity) {
        CommitmentRepository repo = DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository();
        this.owner=owner;
        this.context = context;
        repo.updateMyStepDone();
        this.activity=activity;
    }

    public Category getCategoryForPosition(int position){
        return Category.values()[position];
    }

    @Override
    public SliderProgressBarVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar_item, null);
        Log.d("naed", "creating view holder");
        return new SliderProgressBarVH(inflate, context, owner, activity);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (created[position] != null) {
            created[position].releaseLiveData();
            super.destroyItem(container, position, created[position]);
        }
        created[position] = onCreateViewHolder(container);
        container.addView(created[position].itemView);
        onBindViewHolder(created[position], position);
        return created[position];
    }

    @Override
    public void onBindViewHolder(final SliderProgressBarVH viewHolder, final int position) {
        viewHolder.setCategory(getCategoryForPosition(position));
        viewHolder.progressBarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Float progress = viewHolder.progressBarView.getProgress();
                // progress = (progress+10)%100;
                // viewHolder.progressBarView.setProgress(progress);
                //build data for dialog
                if(!viewHolder.status){
                    DetailsProgressBarDialog dialog = new DetailsProgressBarDialog(context, context.getString(getCategoryForPosition(position).getRes()), getCategoryForPosition(position), owner);
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            viewHolder.status = false;
                        }
                    });
                    viewHolder.status=true;
                    dialog.show();
                }
            }
        });
    }

    @Override
    public int getCount() {
        return Category.values().length;
    }

    static class SliderProgressBarVH extends SliderViewAdapter.ViewHolder {
        final Single<Boolean> first = new Single<>(true);
        View itemView;
        ProgressBarView progressBarView;
        TextView textView_title_page;
        TextView textView_graph_title;
        Button week;
        Button month;
        Button year;
        LineChart chart;
        Period period;
        RecyclerView recyclerView;
        RecyclerView recyclerView_old;
        TextView txt_old;
        View divider_old;

        CommitmentRepository repo = DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository();
        Category category;
        Context context;
        LifecycleOwner owner;
        List<MyStepDoneWithMyStep> newestDay;
        List<MyStepDoneWithMyStep> newestWeek;
        List<MyStepDoneWithMyStep> newestBoth=new ArrayList<>();
        List<MyStepDoneWithMyStep> todayDay;
        List<MyStepDoneWithMyStep> todayWeek;
        List<MyStepDoneWithMyStep> todayBoth=new ArrayList<>();

        LiveData<List<MyStepDoneWithMyStep>> onGoingDay = null;
        LiveData<List<MyStepDoneWithMyStep>> onGoingWeek = null;
        LiveData<List<MyStepDoneWithMyStep>> historyDay = null;
        LiveData<List<MyStepDoneWithMyStep>> historyWeek = null;
        boolean status = false;


        final Observer<List<MyStepDoneWithMyStep>> onGoingObserverDay = new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                todayDay=steps;
                updateProgressBar();
            }
        };
        final Observer<List<MyStepDoneWithMyStep>> onGoingObserverWeek = new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                todayWeek=steps;
                updateProgressBar();
            }
        };

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

        public SliderProgressBarVH(final View itemView, final Context context, LifecycleOwner owner, final FragmentActivity activity) {
            super(itemView);
            chart= itemView.findViewById(R.id.lineChart);
            chart.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            progressBarView = itemView.findViewById(R.id.progressBarItem);
            textView_title_page = itemView.findViewById(R.id.item_category);
            textView_graph_title = itemView.findViewById(R.id.textView_graph_title);
            week = itemView.findViewById(R.id.button_week);
            month = itemView.findViewById(R.id.button_month);
            year = itemView.findViewById(R.id.button_year);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            recyclerView_old = itemView.findViewById(R.id.recycler_view_old);
            txt_old = itemView.findViewById(R.id.txt_old);
            divider_old = itemView.findViewById(R.id.divider_old);

            this.itemView = itemView;
            this.context=context;
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

            this.period=Period.WEEK;

            /* TODO move this in the logout section */
            Button button = itemView.findViewById(R.id.logout);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ServerManager.getInstance(itemView.getContext()).logout(new Lambda() {
                            @Override
                            public Object[] run(Object... paramether) {
                                if((Boolean) paramether[0]) {
                                    Snackbar.make(itemView, R.string.logout_done, BaseTransientBottomBar.LENGTH_LONG);
                                    LoginFragment loginFragment = new LoginFragment();
                                    FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.container, loginFragment, HomeActivity.TAG_LOGIN);
                                    //            transaction.addToBackStack(null);
                                    transaction.commit();
                                } else{
                                    Snackbar.make(itemView, R.string.unknown_error, BaseTransientBottomBar.LENGTH_LONG);
                                }
                                return new Object[0];
                            }
                        });
                    } catch (JSONException e) {
                        Snackbar.make(itemView, R.string.unknown_error, BaseTransientBottomBar.LENGTH_LONG);
                        e.printStackTrace();
                    }
                }
            });
            /* */
        }

        public void update(){
            updateObserver();
            updateProgressBar();
            setLine();
        }

        private void updateObserver(){
            if(historyDay != null){
                historyDay.removeObserver(historyObserverDay);
            }
            historyDay = repo.getStepHistory(category, period.daysAgo(), Period.DAY);
            historyDay.observe(owner, historyObserverDay);
            if(historyWeek != null){
                historyWeek.removeObserver(historyObserverDay);
            }
            historyWeek = repo.getStepHistory(category, period.daysAgo(), Period.WEEK);
            historyWeek.observe(owner, historyObserverWeek);

            if(onGoingDay != null){
                onGoingDay.removeObserver(onGoingObserverDay);
            }
            onGoingDay = repo.getStepOnGoing(category, Period.DAY);
            onGoingDay.observe(owner, onGoingObserverDay);

            if(onGoingWeek != null){
                onGoingWeek.removeObserver(onGoingObserverDay);
            }
            onGoingWeek = repo.getStepOnGoing(category, Period.WEEK);
            onGoingWeek.observe(owner, onGoingObserverWeek);
        }
        private void updateProgressBar(){
            todayBoth.clear();
            if(todayDay!=null){
                todayBoth.addAll(todayDay);
            }
            if(todayWeek!=null){
                todayBoth.addAll(todayWeek);
            }
            if(todayBoth.size() == 0){
                progressBarView.setProgress(0);
            } else {
                float sum = 0;
                float weights = 0;
                for (MyStepDoneWithMyStep step : todayBoth) {
                    sum += step.stepDone.result;
                    weights += step.step.max;
                }
                progressBarView.setProgress(100*sum / weights);
            }
        }
        public void releaseLiveData(){
            if(historyDay != null){
                historyDay.removeObserver(historyObserverDay);
                historyDay=null;
            }
            if(historyWeek != null){
                historyWeek.removeObserver(historyObserverDay);
                historyWeek=null;
            }
            if(onGoingDay != null){
                onGoingDay.removeObserver(onGoingObserverDay);
                onGoingDay=null;
            }
            if(onGoingWeek != null){
                onGoingWeek.removeObserver(onGoingObserverDay);
                onGoingWeek=null;
            }
        }


        private void setCategory(final Category category){
            if(first.getVal()){
                this.category=category;
                first.setVal(false);
                Pair<Integer, Integer> color = category.toColor(context);
                progressBarView.setForegroundColor(color.getX());
                progressBarView.setBackgroundColor(color.getY());
                textView_title_page.setText(context.getString(category.getRes()));
                textView_graph_title.setText(context.getString(R.string.graph_title, context.getString(category.getRes())));
                recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                recyclerView.setAdapter(new GraphAdapter(owner, category));
                recyclerView_old.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                recyclerView_old.setAdapter(new GraphAdapter(owner, category, txt_old, divider_old));
                updateObserver();
            }
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
            Pair<List<Entry>, IAxisValueFormatter> entries = GraphUtil.getGraphData(newestBoth, period);
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
