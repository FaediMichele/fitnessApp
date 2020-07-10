package com.example.crinaed.layout.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

public class LoginFragment extends Fragment {

    public static final int REQUEST_CODE_CHAT = 1;
    EditText email;
    EditText password;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.email_edit);
        password = view.findViewById(R.id.password);
        password.setImeActionLabel(getString(R.string.login_action_label), KeyEvent.KEYCODE_ENTER);
        root = view.findViewById(R.id.root);
        final Button login = view.findViewById(R.id.button_login);
        if(false){ // delete shared preferences ONLY FOR DEBUG
            SharedPreferences settings = getContext().getSharedPreferences(getString(R.string.sessionId), Context.MODE_PRIVATE);
            settings.edit().clear().commit();
        }

//        if(Util.getInstance().checkData(context)){
//            Intent intent = new Intent(context, HomeActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            ActivityCompat.finishAffinity(context);
//            return view;
//
//        }

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
            private long mLastClickTime = 0;

            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                onSubmit();
                login.clearFocus();
            }
        });

        return view;
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

        final Context context = getContext();

        try {
            ServerManager.getInstance(context).login(email.getText().toString(), password.getText().toString(), new Lambda() {
                @Override
                public Object[] run(final Object... paramether) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if((Boolean) paramether[0]) {
                                if((Boolean) paramether[1]) {
                                    if(!paramether[2].equals("")){
                                        Intent intent = new Intent(context, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
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
