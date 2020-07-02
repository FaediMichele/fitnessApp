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
import com.example.crinaed.util.Pair;
import com.example.crinaed.util.Single;

import java.util.List;


/**
 * This is the Dialog that manage the update and view of the MyStepDone value.
 * The logic is in the {@link ProgressBarDetailsAdapter}
 */
public class DetailsProgressBarDialog extends Dialog{

    private Activity activity;
    private Dialog d;
    private LiveData<List<MyStepDoneWithMyStep>> steps;
    private ProgressBarDetailsAdapter adapter;
    private LifecycleOwner owner;
    private String title;

    public DetailsProgressBarDialog(Context context, String title, LiveData<List<MyStepDoneWithMyStep>> steps, LifecycleOwner owner) {
        super(context, R.style.DialogSlideTheme);
        this.activity = (Activity) context;
        this.steps = steps;
        this.title=title;
        this.owner=owner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //gestione interfaccia
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_details_progress_bar);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title = findViewById(R.id.title_details);
        title.setText(this.title);
        final Single<Boolean> first=new Single<>(true);
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        this.steps.observe(owner, new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                //setto la recycle view
                if(first.getVal()) {
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new ProgressBarDetailsAdapter(steps);
                    recyclerView.setAdapter(adapter);
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
