package com.example.crinaed;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MAIN_ACTIVITY_TO_OBJECTIVE_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //delete status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        PagerFragment pagerFragment = new PagerFragment();
        FragmentTransaction transaction0 = getSupportFragmentManager().beginTransaction();
        transaction0.replace(R.id.container, pagerFragment, "AG");
        transaction0.addToBackStack(null);
        transaction0.commit();



        ObjectiveFragment objectiveFragment = new ObjectiveFragment();
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.container_page, objectiveFragment, TAG);
        transaction1.addToBackStack(null);
        transaction1.commit();
    }

}