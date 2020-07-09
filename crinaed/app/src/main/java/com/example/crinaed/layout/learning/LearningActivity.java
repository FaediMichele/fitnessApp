package com.example.crinaed.layout.learning;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.crinaed.R;

public class LearningActivity extends AppCompatActivity {

    public static final String KEY_START_PAGER = "KEY_START_PAGER";
    public static final String TAG_LEARNING_BUY = "LEARNING_ACTIVITY_TO_LEARNING_BUY_FRAGMENT";
    public static final String TAG_PAGER = "LEARNING_ACTIVITY_TO_LEARNING_PAGER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        //delete status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getIntent().getExtras();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(bundle!= null && bundle.getBoolean(KEY_START_PAGER,false)){
            LearningPagerFragment learningPagerFragment = new LearningPagerFragment();
            learningPagerFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_learning, learningPagerFragment, TAG_PAGER);
            //transaction.addToBackStack(null);
            transaction.commit();
        }else{
            LearningBuySearchFragment learningBuySearchFragment = new LearningBuySearchFragment();
            learningBuySearchFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_learning, learningBuySearchFragment, TAG_LEARNING_BUY);
            //transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
