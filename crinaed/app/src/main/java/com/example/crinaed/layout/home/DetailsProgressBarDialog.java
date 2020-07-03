package com.example.crinaed.layout.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.ProgressBarDetails.ProgressBarDetailsAdapter;
import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.MyStepDone;
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.database.repository.CommitmentRepository;
import com.example.crinaed.util.Category;
import com.example.crinaed.util.Pair;
import com.example.crinaed.util.Period;
import com.example.crinaed.util.Single;

import java.util.ArrayList;
import java.util.List;


/**
 * This is the Dialog that manage the update and view of the MyStepDone value.
 * The logic is in the {@link ProgressBarDetailsAdapter}
 */
public class DetailsProgressBarDialog extends Dialog{

    private Activity activity;
    private Dialog d;
    private ProgressBarDetailsAdapter adapter;
    private LifecycleOwner owner;
    private String title;
    private Category category;

    public DetailsProgressBarDialog(Context context, String title, Category category, LifecycleOwner owner) {
        super(context, R.style.DialogSlideTheme);
        this.activity = (Activity) context;
        this.category = category;
        this.title=title;
        this.owner=owner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_details_progress_bar);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title = findViewById(R.id.title_details);
        title.setText(this.title);
        final Single<Boolean> firstDay=new Single<>(true);
        final Single<Boolean> firstWeek=new Single<>(true);
        final Single<Boolean> zeroDay=new Single<>(false);
        final Single<Boolean> zeroWeek=new Single<>(false);
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        final List<MyStepDoneWithMyStep> stepsBoth = new ArrayList<>();

        final LiveData<List<MyStepDoneWithMyStep>> stepsDay = DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().getStepOnGoing(category, Period.DAY);
        final LiveData<List<MyStepDoneWithMyStep>> stepsWeek = DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().getStepOnGoing(category, Period.WEEK);

        stepsDay.observe(owner, new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                if(firstDay.getVal()) {
                    synchronized (stepsBoth) {
                        if (stepsBoth.size() == 0 && !zeroWeek.getVal()) {
                            stepsBoth.addAll(steps);
                            zeroDay.setVal(true);
                            return;
                        }
                        stepsBoth.addAll(steps);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new ProgressBarDetailsAdapter(stepsBoth);
                        recyclerView.setAdapter(adapter);
                        firstDay.setVal(false);
                    }
                } else {
                    adapter.update(steps);
                }
            }
        });

        stepsWeek.observe(owner, new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                if(firstWeek.getVal()) {
                    synchronized (stepsBoth) {
                        if (stepsBoth.size() == 0 && !zeroDay.getVal()) {
                            stepsBoth.addAll(steps);
                            zeroWeek.setVal(true);
                            return;
                        }
                        stepsBoth.addAll(steps);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new ProgressBarDetailsAdapter(stepsBoth);
                        recyclerView.setAdapter(adapter);
                        firstWeek.setVal(false);
                    }

                } else {
                    adapter.update(steps);
                }
            }
        });



        Button confirm = findViewById(R.id.conferma);
        Button del = findViewById(R.id.cancella);

        // retrieve the data from all the holder and save them on the db(also update the percentage)
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<Boolean, MyStepDone[]> data = adapter.getDataToSave();
                if(data.getX()){
                    DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().updateMyStepDone(data.getY());
                }
                dismiss();
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
