package com.example.crinaed.layout.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.database.entity.join.user.UserData;
import com.example.crinaed.database.entity.join.user.UserWithUser;
import com.example.crinaed.layout.social.chat.ChatActivity;
import com.example.crinaed.R;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;

import java.util.ArrayList;
import java.util.List;

public class SocialFragment extends Fragment {
    public static final String TAG_SOCIAL_ARCHIVE = "SOCIAL_FRAGMENT_TO_SOCIAL_ARCHIVE_FRAGMENT";
    final public static int TYPE_VIEW_ITEM_VIEW_ARCHIVE = 0;
    final public static int TYPE_VIEW_VIEW_NORMAL = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        SocialFragment.SocialSearchAdapter adapter = new SocialFragment.SocialSearchAdapter(this, getContext(), getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void receiveData(Object data){
        if(data.equals(HomeActivity.REQUEST_CODE_CHAT)){

        }
    }

    private static class SocialSearchAdapter extends RecyclerView.Adapter<SocialFragment.SocialSearchViewHolder>{

        private List<UserData> newest;
        private List<SocialSearchViewHolder> holders = new ArrayList<>();
        private Context context;
        private LifecycleOwner owner;
        private Activity activity;

        public SocialSearchAdapter(LifecycleOwner owner, final Context context, Activity activity){
            this.context=context;
            this.owner=owner;
            this.activity=activity;
            DatabaseUtil.getInstance().getRepositoryManager().getUserRepository().getData().observe(owner, new Observer<List<UserData>>() {
                @Override
                public void onChanged(List<UserData> userData) {
                    newest=userData;
                    notifyDataSetChanged();
                    for(int i=0; i< holders.size();i++){
                        holders.get(i).updateData(i, newest, context);
                    }
                }
            });

        }

        @NonNull
        @Override
        public SocialFragment.SocialSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView;
            switch(viewType){
                case TYPE_VIEW_ITEM_VIEW_ARCHIVE:
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_social_archive, parent, false);
                    break;
                case TYPE_VIEW_VIEW_NORMAL:
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_social_base, parent, false);
                    break;
                default:
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_social_search, parent, false);
                    break;
            }
            return new SocialSearchViewHolder(itemView, viewType == TYPE_VIEW_ITEM_VIEW_ARCHIVE, owner);
        }

        @Override
        public void onBindViewHolder(@NonNull final SocialFragment.SocialSearchViewHolder holder, int position) {
            if(newest !=null){
                holders.add(holder);
                holder.updateData(position, newest, context);
                final UserData data = this.newest.get(position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ChatActivity.SOCIAL_KEY_ID, String.valueOf(data.user.idUser));
                        bundle.putString(ChatActivity.SOCIAL_KEY_NAME, data.user.firstname);
                        bundle.putString(ChatActivity.SOCIAL_KEY_LAST_NAME, data.user.surname);
                        bundle.putString(ChatActivity.SOCIAL_KEY_EMAIL, data.user.email);
                        bundle.putString(ChatActivity.SOCIAL_KEY_TITLE_OBJECTIVE, data.levels.get(1).cat + ": " + data.levels.get(1).level);
                        bundle.putString(ChatActivity.SOCIAL_KEY_TITLE_STEP, data.levels.get(1).cat + ": " + data.levels.get(1).level);
                        if (data.user.imageDownloaded) {
                            bundle.putString(ChatActivity.SOCIAL_KEY_IMAGE_PATH, data.user.image);
                        }
                        Intent chatIntent = new Intent(context, ChatActivity.class);
                        chatIntent.putExtras(bundle);
                        activity.startActivityForResult(chatIntent, HomeActivity.REQUEST_CODE_CHAT);
                    }
                });
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ServerManager.getInstance(context).blockUser(data.user.idUser, new Lambda() {
                            @Override
                            public Object[] run(Object... paramether) {
                                Toast.makeText(context, context.getString(R.string.unblock_ok), Toast.LENGTH_SHORT).show();
                                return new Object[0];
                            }
                        }, new Lambda() {
                            @Override
                            public Object[] run(Object... paramether) {
                                return new Object[0];
                            }
                        });
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            return SocialFragment.TYPE_VIEW_VIEW_NORMAL;
        }

        @Override
        public int getItemCount() {
            if(newest!=null){
                return newest.size();
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
        TextView level3;
        Button button;
        LifecycleOwner owner;
        Observer<UserWithUser> old=null;
        LiveData<UserWithUser> oldLiveData=null;
        View itemView;

        public SocialSearchViewHolder(@NonNull View itemView, boolean isArchive, LifecycleOwner owner) {
            super(itemView);
            if(!isArchive){
                this.imageView = itemView.findViewById(R.id.image_video);
                this.nameLastName = itemView.findViewById(R.id.name_last_name);
                this.email = itemView.findViewById(R.id.email);
                this.objective = itemView.findViewById(R.id.objective);
                this.step = itemView.findViewById(R.id.step);
                this.level3 = itemView.findViewById(R.id.level3);
                this.button = itemView.findViewById(R.id.button_unblock);
            }
            this.owner=owner;
            this.itemView=itemView;
        }

        public void updateData(int position, List<UserData> newData, Context context){
            if(newData.size()>position) {
                UserData data = newData.get(position);
                setUserData(data, context);
                setIdFriendship(data.user.idUser);
            }
        }


        public void setIdFriendship(final long idFriend) {
            if(oldLiveData!=null) {
                oldLiveData.removeObserver(old);
            }
            oldLiveData= DatabaseUtil.getInstance().getRepositoryManager().getFriendRepository().getFriendshipByFriend(idFriend);
            old = new Observer<UserWithUser>() {
                    @Override
                    public void onChanged(UserWithUser userWithUser) {
                        if(userWithUser!=null) {
                            if (userWithUser.friendship.blocked == Util.getInstance().getIdUser()) {
                                button.setVisibility(View.VISIBLE);
                            } else {
                                button.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                };
            oldLiveData.observe(owner,old);
        }

        public void setUserData(UserData userData, Context context){
            Log.d("naed", "!!!!!image setted: " + userData.user.imageDownloaded);
            if(userData.user.imageDownloaded && imageView!=null) {
                imageView.setImageURI(Uri.parse(userData.user.image));
                Log.d("naed", "!!!!!image setted: " + imageView);

            }
            nameLastName.setText(context.getString(R.string.name_surname, userData.user.firstname, userData.user.surname));
            email.setText(userData.user.email);
            objective.setText(itemView.getContext().getString(R.string.cat_level, userData.levels.get(0).cat, userData.levels.get(0).level));
            step.setText(itemView.getContext().getString(R.string.cat_level, userData.levels.get(1).cat,  userData.levels.get(1).level));
            level3.setText(itemView.getContext().getString(R.string.cat_level, userData.levels.get(2).cat,  userData.levels.get(2).level));
        }
    }
}
