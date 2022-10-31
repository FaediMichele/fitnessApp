package com.example.crinaed.layout.home.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.crinaed.R;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.layout.home.HomeActivity;
import com.example.crinaed.util.Lambda;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register, container, false);

        final EditText name = view.findViewById(R.id.name);
        final EditText lastName = view.findViewById(R.id.last_name);
        final EditText email = view.findViewById(R.id.email);
        final EditText password = view.findViewById(R.id.password);
        final Button buttonRegister = view.findViewById(R.id.button_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(name.getText().length()==0){
                    name.setError(getString(R.string.error_name));
                }
                if(lastName.getText().length()==0){
                    lastName.setError(getString(R.string.error_lastname));
                }
                if(email.getText().length()==0){
                    email.setError(getString(R.string.error_email));
                }
                if(password.getText().length()<6){
                    password.setError(getString(R.string.error_password));
                }
                ServerManager.getInstance(getContext()).createUser(name.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString(), new Lambda() {
                    @Override
                    public Object[] run(Object... paramether) {
                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return new Object[0];
                    }
                }, new Lambda() {
                    @Override
                    public Object[] run(Object... paramether) {
                        Snackbar.make(view, R.string.connection_error, BaseTransientBottomBar.LENGTH_LONG);
                        return new Object[0];
                    }
                });

            }
        });


        return view;
    }

}
