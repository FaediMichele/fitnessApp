package com.example.crinaed.layout.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.layout.home.login.LoginFragment;
import com.example.crinaed.util.Util;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG_PAGER = "MAIN_ACTIVITY_TO_PAGER_FRAGMENT";
    public static final String TAG_LOGIN = "MAIN_ACTIVITY_TO_LOGIN_FRAGMENT";
    public static final int REQUEST_CODE_CHAT = 1;
    public PagerFragment pagerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("cri","parte HomeActivity");

        DatabaseUtil.getInstance().setApplication(this);

        setContentView(R.layout.activity_main);
        //delete status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(false){ // delete shared preferences ONLY FOR DEBUG
            SharedPreferences settings = this.getSharedPreferences(getString(R.string.sessionId), Context.MODE_PRIVATE);
            settings.edit().clear().commit();
        }

        if(!Util.getInstance().checkData(this)){
            LoginFragment loginFragment= new LoginFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, loginFragment, TAG_LOGIN);
//            transaction.addToBackStack(null);
            transaction.commit();
        }else{
            pagerFragment = new PagerFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, pagerFragment, TAG_PAGER);
//            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHAT){
            pagerFragment.sendDataToSocial(requestCode);
        }

    }

}