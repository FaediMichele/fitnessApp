package com.example.crinaed.layout.home;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.join.user.UserData;
import com.example.crinaed.layout.social.chat.ChatActivity;
import com.example.crinaed.R;
import com.example.crinaed.layout.social.SocialArchiveFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SocialFragment extends Fragment {
    public static final String TAG_SOCIAL_ARCHIVE = "SOCIAL_FRAGMENT_TO_SOCIAL_ARCHIVE_FRAGMENT";
    final public static int TYPE_VIEW_ITEM_VIEW_ARCHIVE = 0;
    final public static int TYPE_VIEW_VIEW_NORMAL = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        SocialFragment.SocialSearchAdapter adapter = new SocialFragment.SocialSearchAdapter(this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private class SocialSearchAdapter extends RecyclerView.Adapter<SocialFragment.SocialSearchViewHolder>{

        private List<UserData> newest;

        public SocialSearchAdapter(LifecycleOwner owner){
            DatabaseUtil.getInstance().getRepositoryManager().getUserRepository().getData().observe(owner, new Observer<List<UserData>>() {
                @Override
                public void onChanged(List<UserData> userData) {
                    newest = userData;
                    notifyDataSetChanged();
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
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_social_search, parent, false);
                    break;
                default:
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_social_search, parent, false);
                    break;
            }
            return new SocialFragment.SocialSearchViewHolder(itemView, viewType == TYPE_VIEW_ITEM_VIEW_ARCHIVE);
        }

        @Override
        public void onBindViewHolder(@NonNull SocialFragment.SocialSearchViewHolder holder, int position) {
            if(position > 0) {
                if(newest !=null){
                    final UserData data = this.newest.get(position);

                    if(data.user.imageDownloaded) {
                        holder.imageView.setImageURI(Uri.fromFile(new File(data.user.image)));
                    }

                    holder.nameLastName.setText(data.user.firstname + " " + data.user.surname);
                    holder.email.setText(data.user.email);
                    holder.objective.setText(data.levels.get(0).cat + ": " + data.levels.get(0).level);
                    holder.step.setText(data.levels.get(1).cat + ": " + data.levels.get(1).level);
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
                            Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                            chatIntent.putExtras(bundle);
                            startActivity(chatIntent);
                        }
                    });
                }

            }else{
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SocialArchiveFragment socialArchiveFragment = new SocialArchiveFragment();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, socialArchiveFragment, TAG_SOCIAL_ARCHIVE);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

            }
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? SocialFragment.TYPE_VIEW_ITEM_VIEW_ARCHIVE : SocialFragment.TYPE_VIEW_VIEW_NORMAL;
        }

        @Override
        public int getItemCount() {
            if(newest!=null){
                return newest.size();
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

        public SocialSearchViewHolder(@NonNull View itemView, boolean isArchive) {
            super(itemView);
            if(!isArchive){
                this.imageView = itemView.findViewById(R.id.image_profile);
                this.nameLastName = itemView.findViewById(R.id.name_last_name);
                this.email = itemView.findViewById(R.id.email);
                this.objective = itemView.findViewById(R.id.objective);
                this.step = itemView.findViewById(R.id.step);
            }
        }
    }
}
