package com.example.crinaed.layout.social;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.join.user.UserData;
import com.example.crinaed.layout.social.chat.ChatActivity;
import com.example.crinaed.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SocialSearchActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Social);
        setContentView(R.layout.activity_social_search);
        //delete status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SocialSearchAdapter adapter = new SocialSearchAdapter(this);
        recyclerView.setAdapter(adapter);

    }

    private class SocialSearchAdapter extends RecyclerView.Adapter<SocialSearchViewHolder>{

        private List<UserData> newestData = null;

        public SocialSearchAdapter(LifecycleOwner owner){
            DatabaseUtil.getInstance().getRepositoryManager().getUserRepository().getData().observe(owner, new Observer<List<UserData>>() {
                @Override
                public void onChanged(List<UserData> userData) {
                    newestData=userData;
                    notifyDataSetChanged();
                }
            });
        }

        @NonNull
        @Override
        public SocialSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_social_search, parent, false);
            return new SocialSearchViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final SocialSearchViewHolder holder, final int position) {

            if(newestData != null) {
                final UserData user = newestData.get(position);
                if(user.user.imageDownloaded){
                    holder.imageView.setImageURI(Uri.fromFile(new File(user.user.image)));
                }

                holder.nameLastName.setText(user.user.firstname + " " + user.user.surname);
                holder.email.setText(user.user.email);
                holder.objective.setText(user.levels.get(0).cat + ": " + user.levels.get(0).level);
                holder.step.setText(user.levels.get(1).cat + ": " + user.levels.get(1).level);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ChatActivity.SOCIAL_KEY_ID, String.valueOf(user.user.idUser));
                        bundle.putString(ChatActivity.SOCIAL_KEY_NAME, user.user.firstname);
                        bundle.putString(ChatActivity.SOCIAL_KEY_LAST_NAME, user.user.surname);
                        bundle.putString(ChatActivity.SOCIAL_KEY_EMAIL, user.user.email);
                        bundle.putString(ChatActivity.SOCIAL_KEY_TITLE_OBJECTIVE, user.levels.get(0).cat + ": " + user.levels.get(0).level);
                        bundle.putString(ChatActivity.SOCIAL_KEY_TITLE_STEP, user.levels.get(1).cat + ": " + user.levels.get(1).level);
                        Intent chatIntent = new Intent(getApplicationContext(), ChatActivity.class);
                        chatIntent.putExtras(bundle);
                        startActivity(chatIntent);
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            if(this.newestData != null){
                return this.newestData.size();
            }
            return 0;
        }
    }

    private static class SocialSearchViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView nameLastName;
        TextView email;
        TextView objective;
        TextView step;

        public SocialSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_profile);
            this.nameLastName  = itemView.findViewById(R.id.name_last_name);
            this.email = itemView.findViewById(R.id.email);
            this.objective  = itemView.findViewById(R.id.objective);
            this.step = itemView.findViewById(R.id.step);
        }
    }
}
