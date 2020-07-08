package com.example.crinaed.layout.learning;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.crinaed.R;

public class LearningActivity extends AppCompatActivity {

    public static final String TAG_LEARNING_BUY = "LEARNING_ACTIVITY_TO_LEARNING_BUY_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        //delete status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getIntent().getExtras();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LearningBuySearchFragment learningBuyFragment = new LearningBuySearchFragment();
        learningBuyFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_learning, learningBuyFragment, TAG_LEARNING_BUY);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
