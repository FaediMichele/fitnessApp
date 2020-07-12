package com.example.crinaed.layout.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.VolleyError;
import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.NetworkUtil;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.layout.home.HomeActivity;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CHAT = 1;
    EditText email;
    EditText password;
    View root;
    final AppCompatActivity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        //delete status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DatabaseUtil.getInstance().setApplication(this);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password.setImeActionLabel(getString(R.string.login_action_label), KeyEvent.KEYCODE_ENTER);
        root = findViewById(R.id.root);



        final Button login = findViewById(R.id.button_login);
        if(false){ // delete shared preferences ONLY FOR DEBUG
            SharedPreferences settings = getSharedPreferences(getString(R.string.sessionId), Context.MODE_PRIVATE);
            settings.edit().clear().commit();
        }


        if(Util.getInstance().checkData(this)){
            Intent intent = new Intent(activity, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            ActivityCompat.finishAffinity(this);
            return;

        }

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm!=null){
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return onSubmit();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
                login.clearFocus();
            }
        });
    }

    private boolean onSubmit(){
        if(!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            email.setError(getString(R.string.wrong_email));
            email.requestFocus();
            return false;
        }
        if(password.getText().length()<6){
            password.setError(getString(R.string.wrong_password));
            password.requestFocus();
            return false;
        }
        try {
            ServerManager.getInstance(this).login(email.getText().toString(), password.getText().toString(), new Lambda() {
                @Override
                public Object[] run(final Object... paramether) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if((Boolean) paramether[0]) {
                                if((Boolean) paramether[1]) {
                                    if(!paramether[2].equals("")){
                                        Intent intent = new Intent(activity, HomeActivity.class);
                                        startActivity(intent);
                                    } else{
                                        Snackbar.make(root, getString(R.string.wrong_data), Snackbar.LENGTH_LONG).show();
                                    }

                                } else{
                                    Snackbar.make(root, getString(R.string.wrong_credential), Snackbar.LENGTH_LONG).show();
                                }
                            } else{
                                Snackbar.make(root, getString(R.string.cannot_login), Snackbar.LENGTH_LONG).show();
                                try{
                                   Log.d("naed", "error on login: " + ((VolleyError) paramether[1]).getMessage());
                                } catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                    return new Object[0];
                }
            });

            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

}