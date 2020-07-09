package com.example.crinaed.layout.social;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.database.ServerManager;
import com.example.crinaed.layout.home.HomeActivity;
import com.example.crinaed.layout.social.chat.ChatActivity;
import com.example.crinaed.R;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Pair;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SocialSearchActivity extends AppCompatActivity {
    SocialSearchAdapter adapter;
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
        final View root = findViewById(R.id.root);
        adapter = new SocialSearchAdapter(this, root);
        recyclerView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }



    private class SocialSearchAdapter extends RecyclerView.Adapter<SocialSearchViewHolder>{

        private List<Pair<JSONObject, String>> newestData = new ArrayList<>();
        private SearchHelper helper;

        public SocialSearchAdapter(final Context context, final View root){
            helper= new SearchHelper(getCacheDir(), context);
            newestData.clear();
            notifyDataSetChanged();
            helper.getFriendshipRequest(new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    newestData.add(new Pair<>((JSONObject) paramether[0], ((File) paramether[1]).getAbsolutePath()));
                    notifyItemInserted(newestData.size() - 1);
                    return new Object[0];
                }
            }, new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    Snackbar.make(root, R.string.connection_error, BaseTransientBottomBar.LENGTH_LONG).show();
                    return new Object[0];
                }
            });
        }

        public void search(String text){
            newestData.clear();
            notifyDataSetChanged();
            helper.search(text, new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    newestData.add(new Pair<>((JSONObject)paramether[0], ((File) paramether[1]).getAbsolutePath()));
                    notifyItemInserted(newestData.size()-1);
                    return new Object[0];
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
                holder.setData(newestData.get(position));
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

    private class SocialSearchViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView nameLastName;
        TextView email;
        TextView objective;
        TextView step;
        TextView level3;
        Button sendRequest;
        View itemView;

        public SocialSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_video);
            this.nameLastName  = itemView.findViewById(R.id.name_last_name);
            this.email = itemView.findViewById(R.id.email);
            this.objective  = itemView.findViewById(R.id.objective);
            this.step = itemView.findViewById(R.id.step);
            this.sendRequest = itemView.findViewById(R.id.button_friend_request);
            this.level3 = itemView.findViewById(R.id.level3);
            this.itemView=itemView;
        }

        public void setData(final Pair<JSONObject, String> data){
            final JSONObject obj = data.getX();
            try{
                final String firstname = obj.getString("firstname");
                final String surname = obj.getString("surname");
                final String emailText = obj.getString("email");
                String objectiveText="";
                String stepText="";
                nameLastName.setText(itemView.getContext().getString(R.string.name_surname, firstname, surname));
                email.setText(emailText);
                try{
                    objective.setText(obj.getString("nameCommitment"));
                    step.setText(obj.getString("nameStep"));
                }catch (JSONException ignore) {
                }
                try{
                    JSONArray levels = obj.getJSONArray("Levels");
                    JSONObject level1 = levels.getJSONObject(0);
                    objective.setText(itemView.getContext().getString(R.string.cat_level, level1.getString("cat") , level1.getInt("level")));
                    JSONObject level2 = levels.getJSONObject(1);
                    step.setText(itemView.getContext().getString(R.string.cat_level, level2.getString("cat") , level2.getInt("level")));
                    JSONObject level3Obj= levels.getJSONObject(2);
                    level3.setText(itemView.getContext().getString(R.string.cat_level, level3Obj.getString("cat"), level3Obj.getInt("level")));
                    level3.setVisibility(View.VISIBLE);
                }catch (JSONException ignore) {
                }
                this.imageView.setImageURI(Uri.parse(data.getY()));
                try{
                    if(obj.getBoolean("isFriend")){
                        final String obT = objectiveText;
                        final String stT = stepText;
                        sendRequest.setVisibility(View.INVISIBLE);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                try {
                                    bundle.putString(ChatActivity.SOCIAL_KEY_ID, obj.getString("idUser"));
                                    bundle.putString(ChatActivity.SOCIAL_KEY_NAME, firstname);
                                    bundle.putString(ChatActivity.SOCIAL_KEY_LAST_NAME, surname);
                                    bundle.putString(ChatActivity.SOCIAL_KEY_EMAIL, emailText);
                                    bundle.putString(ChatActivity.SOCIAL_KEY_TITLE_OBJECTIVE, obT);
                                    bundle.putString(ChatActivity.SOCIAL_KEY_TITLE_STEP, stT);
                                    if (!data.getY().equals("")) {
                                        bundle.putString(ChatActivity.SOCIAL_KEY_IMAGE_PATH, data.getY());
                                    }
                                    Intent chatIntent = new Intent(getBaseContext(), ChatActivity.class);
                                    chatIntent.putExtras(bundle);
                                    startActivityForResult(chatIntent, HomeActivity.REQUEST_CODE_CHAT);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                }catch (JSONException ignore) {
                }
                try{
                    if(obj.getBoolean("requestSended")){
                        sendRequest.setText(R.string.request_sended);
                        sendRequest.setEnabled(false);
                    }

                }catch (JSONException ignore) {
                }
                try{
                    if(obj.getBoolean("requestReceived")){
                        sendRequest.setText(R.string.request_received);
                        sendRequest.setEnabled(true);
                    }
                }catch (JSONException ignore) {
                }
            } catch (JSONException ignore) {
            }
            if(sendRequest.isEnabled()){
                sendRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            ServerManager.getInstance(getApplicationContext()).sendRequestFriendship(obj.getLong("idUser"), new Lambda() {
                                @Override
                                public Object[] run(final Object... paramether) {
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            try{
                                                if((Boolean) paramether[0]){
                                                    Toast.makeText(getApplicationContext(), getString(R.string.request_sended), Toast.LENGTH_SHORT).show();
                                                    sendRequest.setText(R.string.request_sended);
                                                    sendRequest.setEnabled(false);
                                                }
                                            } catch (Exception e){
                                                e.printStackTrace();
                                                try{
                                                    Toast.makeText(getBaseContext(), getString(R.string.request_accepted), Toast.LENGTH_SHORT).show();
                                                    sendRequest.setVisibility(View.GONE);
                                                }catch (Exception e1) {
                                                    e1.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                                    return new Object[0];
                                }
                            }, new Lambda() {
                                @Override
                                public Object[] run(Object... paramether) {
                                    return new Object[0];
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
