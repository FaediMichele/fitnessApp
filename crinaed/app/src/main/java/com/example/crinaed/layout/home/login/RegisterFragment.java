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
import com.example.crinaed.layout.home.HomeActivity;

public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        final EditText name = view.findViewById(R.id.name);
        final EditText lastName = view.findViewById(R.id.last_name);
        final EditText email = view.findViewById(R.id.email);
        final EditText password = view.findViewById(R.id.password);
        final Button buttonRegister = view.findViewById(R.id.button_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fare le chiamate al server per creare un nuovo utente e se tutto andando a buon fine lanciare la home activity in questo modo
                Intent intent = new Intent(getContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        return view;
    }

}
