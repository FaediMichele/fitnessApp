package com.example.crinaed;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.crinaed.ProgressBar.SliderProgressBarModel;
import com.example.crinaed.ProgressBarTest.TestModel.ProgressBarModel;
import com.google.android.material.textfield.TextInputEditText;

public class DetailsProgressBarDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public SliderProgressBarModel progressBarModel;

    public DetailsProgressBarDialog(Context a, SliderProgressBarModel progressBarModel) {
        super(a,R.style.DialogSlideTheme);
        // TODO Auto-generated constructor stub
        this.c = (Activity) a;
        this.progressBarModel = progressBarModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_details_progress_bar);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView valueTry = findViewById(R.id.value_try);
        valueTry.setText(this.progressBarModel.getStepList().get(0).getDescription());

        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                c.finish();
                break;
            case R.id.btn_no:
                EditText editText = findViewById(R.id.edit_text_try);
                this.progressBarModel.getStepList().get(0).setProgressPercentage(Double.parseDouble(editText.getText().toString()));
                Log.d("cri", "sono qui");
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
