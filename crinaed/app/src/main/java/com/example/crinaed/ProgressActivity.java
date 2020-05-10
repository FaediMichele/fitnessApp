package com.example.crinaed;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crinaed.util.ArcProgress;

public class ProgressActivity extends AppCompatActivity {
    private int progress = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        findViewById(R.id.progressBar2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress=(progress+10)%100;
                ((ArcProgress) findViewById(R.id.progressBar2)).setProgress(progress);
            }
        });
    }
}
