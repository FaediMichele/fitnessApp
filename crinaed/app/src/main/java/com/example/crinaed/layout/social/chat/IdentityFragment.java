package com.example.crinaed.layout.social.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.crinaed.R;

public class IdentityFragment extends Fragment {

    public static final String TAG_CHAT = "IDENTITY_FRAGMENT_TO_CHAT_FRAGMENT";
    public static final String TAG_BACK_STECK = "BACK_STACK_IDENTITY_FRAGMENT_TO_CHAT_FRAGMENT";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identity, container, false);

        final Bundle dataForChatActivity = getArguments();
        TextView name = view.findViewById(R.id.name);
        TextView lastName = view.findViewById(R.id.last_name);
        TextView email = view.findViewById(R.id.email);
        name.setText(dataForChatActivity.getString(ChatActivity.SOCIAL_KEY_NAME));
        lastName.setText(dataForChatActivity.getString(ChatActivity.SOCIAL_KEY_LAST_NAME));
        email.setText(dataForChatActivity.getString(ChatActivity.SOCIAL_KEY_EMAIL));
        view.findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}
