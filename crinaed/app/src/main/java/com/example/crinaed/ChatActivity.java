package com.example.crinaed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ChatActivity extends AppCompatActivity {

    public static final String TAG_PAGER = "MAIN_ACTIVITY_TO_OBJECTIVE_FRAGMENT";
    public static final String SOCIAL_KEY_ID = "ID";
    public static final String SOCIAL_KEY_NAME = "NAME";
    public static final String SOCIAL_KEY_LAST_NAME = "LAST_NAME";
    public static final String SOCIAL_KEY_EMAIL = "EMAIL";
    public static final String SOCIAL_KEY_TITLE_OBJECTIVE = "TITLE_OBJECTIVE";
    public static final String SOCIAL_KEY_TITLE_STEP = "TITLE_STEP";

    String id;
    TextView name;
    TextView lastName;
    ImageView imageProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Social);
        setContentView(R.layout.activity_chat);
        //delete status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.name = findViewById(R.id.name);
        this.lastName = findViewById(R.id.last_name);
        this.imageProfile = findViewById(R.id.image_profile);

        Bundle dataForChatActivity = getIntent().getExtras();
        this.id = dataForChatActivity.getString(ChatActivity.SOCIAL_KEY_ID);
        this.name.setText(dataForChatActivity.getString(SOCIAL_KEY_NAME));
        this.lastName.setText(dataForChatActivity.getString(SOCIAL_KEY_LAST_NAME));
    }
}
