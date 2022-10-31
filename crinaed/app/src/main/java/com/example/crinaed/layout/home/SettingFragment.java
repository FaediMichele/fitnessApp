package com.example.crinaed.layout.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.database.entity.join.user.UserData;
import com.example.crinaed.layout.home.login.LoginFragment;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_setting, container, false);
        final TextView name = view.findViewById(R.id.name);
        final TextView last_name = view.findViewById(R.id.last_name);
        final TextView email = view.findViewById(R.id.email);
        DatabaseUtil.getInstance().getRepositoryManager().getUserRepository().getUserById(Util.getInstance().getIdUser()).observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if(userData!=null) {
                    name.setText(getString(R.string.name_formatted, userData.user.firstname));
                    last_name.setText(getString(R.string.last_name_formatted, userData.user.surname));
                    email.setText(getString(R.string.email_formatted, userData.user.email));
                }
            }
        });

        View button = view.findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ServerManager.getInstance(view.getContext()).logout(new Lambda() {
                        @Override
                        public Object[] run(Object... paramether) {
                            if((Boolean) paramether[0]) {
                                Snackbar.make(view, R.string.logout_done, BaseTransientBottomBar.LENGTH_LONG);
                                LoginFragment loginFragment = new LoginFragment();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, loginFragment, HomeActivity.TAG_LOGIN);
                                // transaction.addToBackStack(null);
                                transaction.commit();
                            } else{
                                Snackbar.make(view, R.string.unknown_error, BaseTransientBottomBar.LENGTH_LONG);
                            }
                            return new Object[0];
                        }
                    });
                } catch (JSONException e) {
                    Snackbar.make(view, R.string.unknown_error, BaseTransientBottomBar.LENGTH_LONG);
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}
