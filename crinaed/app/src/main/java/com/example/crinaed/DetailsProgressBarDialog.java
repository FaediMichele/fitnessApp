package com.example.crinaed;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.ProgressBar.SliderProgressBarModel;
import com.example.crinaed.ProgressBarDetails.ProgressBarDetailsAdapter;

public class DetailsProgressBarDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    public Dialog d;
    public Button conferma, cancella;
    public SliderProgressBarModel progressBarModel;
    private ProgressBarDetailsAdapter adapter;

    public DetailsProgressBarDialog(Context context, SliderProgressBarModel progressBarModel) {
        super(context,R.style.DialogSlideTheme);
        this.activity = (Activity) context;
        this.progressBarModel = progressBarModel;
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
        title.setText(this.progressBarModel.getTitle());

        //setto la recycle view
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProgressBarDetailsAdapter(this.progressBarModel.getStepList());
        recyclerView.setAdapter(adapter);

        //gestione bottoni
        conferma = (Button) findViewById(R.id.conferma);
        cancella = (Button) findViewById(R.id.cancella);
        conferma.setOnClickListener(this);
        cancella.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_yes:
//                c.finish();
//                break;
//            case R.id.btn_no:
//                EditText editText = findViewById(R.id.edit_text_try);
//                this.progressBarModel.getStepList().get(0).setProgressPercentage(Double.parseDouble(editText.getText().toString()));
//                Log.d("cri", "sono qui");
//                dismiss();
//                break;
//            default:
//                break;
//        }
        dismiss();
    }
}
