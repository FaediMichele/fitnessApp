package com.example.crinaed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class IdentityFragment extends Fragment {

    public static final String TAG_CHAT = "IDENTITY_FRAGMENT_TO_CHAT_FRAGMENT";
    public static final String TAG_BACK_STECK = "BACK_STACK_IDENTITY_FRAGMENT_TO_CHAT_FRAGMENT";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identity, container, false);
        view.findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}
