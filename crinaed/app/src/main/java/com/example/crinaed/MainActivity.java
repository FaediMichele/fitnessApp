package com.example.crinaed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static private String KEY = "THEME";
    static private String VALUE_DARK = "DARK_THEME";
    static private String VALUE_LIGHT = "LIGHT_THEME";
    static private String ID_APPLICATION = "ID_APPLICATION";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences preferences = getSharedPreferences(ID_APPLICATION,MODE_PRIVATE);
        if(VALUE_LIGHT.equals(preferences.getString(KEY,VALUE_LIGHT))){
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.ThemeCri);
        }
        setContentView(R.layout.activity_main);

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
