package com.example.crinaed.layout.social.chat;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.crinaed.R;

public class ChatActivity extends AppCompatActivity {

    public static final String SOCIAL_KEY_ID = "ID";
    public static final String SOCIAL_KEY_EMAIL = "EMAIL";
    public static final String SOCIAL_KEY_NAME = "NAME";
    public static final String SOCIAL_KEY_LAST_NAME = "LAST_NAME";
    public static final String SOCIAL_KEY_TITLE_OBJECTIVE = "TITLE_OBJECTIVE";
    public static final String SOCIAL_KEY_TITLE_STEP = "TITLE_STEP";
    public static final String SOCIAL_KEY_IMAGE_PATH = "IMAGE_PATH";
    public static final String TAG_CHAT = "CHAT_ACTIVITY_TO_CHAT_FRAGMENT";
    public static final String TAG_KEY_FRIENDSHIP = "KEY_FRIENDSHIP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {//macna il back e le informazioni dell'utente
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Social);
        setContentView(R.layout.activity_chat);
        Bundle bundle = getIntent().getExtras();
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundle);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_chat, chatFragment, TAG_CHAT);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
