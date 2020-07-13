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
    private ProgressBarDetailsAdapter adapter;
    private LifecycleOwner owner;
    private String title;
    private long idCommitment;

    public DetailsProgressBarDialog(Context context, String title, long idCommitment, LifecycleOwner owner) {
        super(context, R.style.DialogSlideTheme);
        this.activity = (Activity) context;
        this.idCommitment = idCommitment;
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
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final Single<Boolean> first = new Single<>(true);
        final List<MyStepDoneWithMyStep> both = new ArrayList<>();
        final List<MyStepDoneWithMyStep> newestDay = new ArrayList<>();
        final List<MyStepDoneWithMyStep> newestWeek = new ArrayList<>();
        DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().getStepOnGoingByIdCommitment(idCommitment, Period.DAY).observe(owner, new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                newestDay.clear();
                newestDay.addAll(steps);
                both.clear();
                if(newestWeek!=null){
                    both.addAll(newestWeek);
                }
                both.addAll(newestDay);

                if(first.getVal()) {
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new ProgressBarDetailsAdapter(both, getContext());
                    recyclerView.setAdapter(adapter);
                    first.setVal(false);
                } else {
                    adapter.update(both);
                }
            }
        });
        DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().getStepOnGoingByIdCommitment(idCommitment, Period.WEEK).observe(owner, new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                newestWeek.clear();
                newestWeek.addAll(steps);
                both.clear();
                if(newestDay!=null){
                    both.addAll(newestDay);
                }
                both.addAll(newestWeek);

                if(first.getVal()) {
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new ProgressBarDetailsAdapter(both, getContext());
                    recyclerView.setAdapter(adapter);
                    first.setVal(false);
                } else {
                    adapter.update(both);
                }
            }
        });

        Button confirm = findViewById(R.id.confirm_button);
        Button del = findViewById(R.id.delete_button);

        // retrieve the data from all the holder and save them on the db(also update the percentage)
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStepDone[] data = adapter.getDataToSave();
                DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().updateMyStepDone(data);
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
