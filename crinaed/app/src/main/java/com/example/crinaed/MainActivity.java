package com.example.crinaed;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.util.Util;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_PAGER = "MAIN_ACTIVITY_TO_OBJECTIVE_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //delete status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        PagerFragment pagerFragment = new PagerFragment();
        FragmentTransaction transaction0 = getSupportFragmentManager().beginTransaction();
        transaction0.replace(R.id.container, pagerFragment, TAG_PAGER);
        transaction0.addToBackStack(null);
        transaction0.commit();

        DatabaseUtil.getInstance().setApplication(this);

        /* TODO Change the following code with the page of the login */
        if(true){ // delete shared preferences ONLY FOR DEBUG
            SharedPreferences settings = getSharedPreferences(getString(R.string.sessionId), Context.MODE_PRIVATE);
            settings.edit().clear().commit();
        }

        if(!Util.getInstance().checkData(this)){
            try {
                ServerManager.getInstance(this).login("ciaobello", "p");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        ObjectiveFragment objectiveFragment = new ObjectiveFragment();
//        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
//        transaction1.replace(R.id.container_page, objectiveFragment, TAG_CONTAINER);
//        transaction1.addToBackStack(null);
//        transaction1.commit();
    }

}