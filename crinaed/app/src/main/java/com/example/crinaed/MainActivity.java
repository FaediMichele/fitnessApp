package com.example.crinaed;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.crinaed.ProgressBar.SliderProgressBar;
import com.example.crinaed.ProgressBar.SliderProgressBarAdapter;
import com.example.crinaed.ProgressBar.SliderProgressBarModel;
import com.example.crinaed.ProgressBar.Step;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MAIN_ACTIVITY_TO_OBJECTIVE_FRAGMENT";

    private ObjectiveFragment objectiveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        objectiveFragment = new ObjectiveFragment();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, objectiveFragment, TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}