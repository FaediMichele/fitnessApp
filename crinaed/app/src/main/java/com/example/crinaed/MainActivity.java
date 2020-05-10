package com.example.crinaed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    static private String KEY = "THEME";
    static private String VALUE_DARK = "DARK_THEME";
    static private String VALUE_LIGHT = "LIGHT_THEME";
    static private String ID_APPLICATION = "ID_APPLICATION";
    static int progress = 10;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.d("log", "i log pp");
        super.onCreate(savedInstanceState);
        final SharedPreferences preferences = getSharedPreferences(ID_APPLICATION,MODE_PRIVATE);
        if(VALUE_LIGHT.equals(preferences.getString(KEY,VALUE_LIGHT))){
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.ThemeCri);
        }
        setContentView(R.layout.activity_main);
        progress += 10;
        ProgressBar bar = findViewById(R.id.progressBar);
        bar.setProgress(progress);
        findViewById(R.id.setting_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(VALUE_LIGHT.equals(preferences.getString(KEY,VALUE_LIGHT))){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(KEY,VALUE_DARK);
                    editor.apply();
                }
                else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(KEY,VALUE_LIGHT);
                    editor.apply();
                }

                Log.d("log", "progress.getProgress() = " + progress);

                Intent refresh = new Intent(thisActivity(), MainActivity.class);
                startActivity(refresh);
                thisActivity().finish();

            }
        });


        findViewById(R.id.button_change_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(VALUE_LIGHT.equals(preferences.getString(KEY,VALUE_LIGHT))){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(KEY,VALUE_DARK);
                    editor.apply();
                }
                else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(KEY,VALUE_LIGHT);
                    editor.apply();

                }
                Intent refresh = new Intent(thisActivity(), MainActivity.class);
                startActivity(refresh);
                thisActivity().finish();
            }
        });

    }

    private AppCompatActivity thisActivity(){
        return this;
    }
}
