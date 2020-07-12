package com.example.crinaed.layout.objective;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.crinaed.R;

public class ObjectiveActivity extends AppCompatActivity {

    public static final String TAG_MANAGEMENT = "OBJECTIVE_ACTIVITY_TO_OBJECTIVE_MANAGEMENT_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective);
        //delete status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ObjectivesManagementFragment objectivesManagementFragment = new ObjectivesManagementFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, objectivesManagementFragment,TAG_MANAGEMENT);
        //transaction.addToBackStack(null);
        transaction.commit();


//        ObjectiveStepCreateFragment createCommitmentFragment = new ObjectiveStepCreateFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.container, createCommitmentFragment);
//        //transaction.addToBackStack(null);
//        transaction.commit();
    }
}
