package com.example.crinaed.layout.social.chat;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.FriendMessage;
import com.example.crinaed.database.entity.Friendship;
import com.example.crinaed.database.entity.join.user.UserWithUser;
import com.example.crinaed.database.repository.FriendRepository;
import com.example.crinaed.util.Single;
import com.example.crinaed.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatFragment extends Fragment {

    public static final String TAG_IDENTITY = "CHAT_FRAGMENT_TO_IDENTITY_FRAGMENT";
    public static final String TAG_BACK_STECK = "BACK_STACK_CHAT_FRAGMENT_TO_IDENTITY_FRAGMENT";

    static final public int SENT_LAYOUT = 0;
    static final public int RECEIVE_LAYOUT = 1;

    private TextView lastName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat, container, false);

        //delete status bar
        TextView nameAndLastName = view.findViewById(R.id.name_and_last_name);
        final ImageView imageProfile = view.findViewById(R.id.image_profile);

        final Bundle dataForChatActivity = getArguments();
        long id = -1;
        if (dataForChatActivity == null || getActivity() == null) {
            return view;
        }

        String tmp = dataForChatActivity.getString(ChatActivity.SOCIAL_KEY_ID);
        if(tmp != null){
            id = Long.parseLong(tmp);
        }

        if(id == -1){
            /* TODO Handle this situation if needed */
        }




        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final Single<Boolean> first = new Single<>(true);

        // get the friendship by it's id, and use the id to get the message.
        // for the first time also set the listener for the send
        DatabaseUtil.getInstance().getRepositoryManager().getFriendRepository().getFriendshipByFriend(id).observe(this, new Observer<UserWithUser>() {
            @Override
            public void onChanged(final UserWithUser friendships) {
                if(friendships.user2.imageDownloaded) {
                    imageProfile.setImageURI(Uri.parse(friendships.user2.image));
                }
                final ChatFragment.ChatAdapter adapter = new ChatAdapter(getActivity(), friendships.friendship.idFriendship, friendId(friendships));
                recyclerView.setAdapter(adapter);
                if(first.getVal()){
                    first.setVal(false);
                    final EditText editText = view.findViewById(R.id.text_send);
                    view.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.addItem(editText.getText().toString());
                            editText.setText("");
                            if(recyclerView.getLayoutManager() != null) {
                                recyclerView.getLayoutManager().scrollToPosition(adapter.newest.size()-1);
                            }
                        }
                    });
                }
            }
        });
        imageProfile.setImageDrawable(getActivity().getDrawable(R.drawable.simple_people));


        nameAndLastName.setText(getString(R.string.name_surname, dataForChatActivity.getString(ChatActivity.SOCIAL_KEY_NAME), dataForChatActivity.getString(ChatActivity.SOCIAL_KEY_LAST_NAME)));
        //set recycler view

        //set click listener
        view.findViewById(R.id.back_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() != null) {
                    getActivity().finish();
                }
            }
        });



        nameAndLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IdentityFragment identityFragment = new IdentityFragment();
                identityFragment.setArguments(dataForChatActivity);
                if(getActivity() != null) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container_chat, identityFragment, TAG_IDENTITY);
                    transaction.addToBackStack(TAG_BACK_STECK);
                    transaction.commit();
                }
            }
        });
        return view;
    }

    private long friendId(UserWithUser userWithUser){
        return  userWithUser.user1.idUser==Util.getInstance().getIdUser() ? userWithUser.user2.idUser : userWithUser.user1.idUser;
    }


    private static class ChatAdapter extends RecyclerView.Adapter<ChatFragment.ChatVH>{

        private List<FriendMessage> newest=null; // sorted by date DESC
        private FriendRepository repo;
        private long idFriendship;
        private long idFriend;


        public ChatAdapter(LifecycleOwner owner, long idFriendship, long idFriend) {
            repo = DatabaseUtil.getInstance().getRepositoryManager().getFriendRepository();
            repo.getMessageByIdFriendship(idFriendship).observe(owner, new Observer<List<FriendMessage>>() {
                @Override
                public void onChanged(List<FriendMessage> friendMessages) {
                    newest=friendMessages;
                    notifyDataSetChanged();
                }
            });
            this.idFriend=idFriend;
            this.idFriendship=idFriendship;
        }

        public void addItem(String message){
            // automatically update the newest
            /* TODO add the post to the server here */
            repo.addMessage(new FriendMessage(idFriendship, new Date().getTime(), Util.getInstance().getIdUser(), idFriend, message));
        }



        @NonNull
        @Override
        public ChatFragment.ChatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView;
            switch(viewType){
                case ChatFragment.SENT_LAYOUT:
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_sent,parent,false);
                    break;
                case ChatFragment.RECEIVE_LAYOUT:
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_receive,parent,false);
                    break;
                default :
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_sent,parent,false);
            }
            return new ChatVH(itemView);
        }

        @Override
        public int getItemViewType(int position) {
            if(this.newest.get(position).idSender != Util.getInstance().getIdUser()){
                return ChatFragment.SENT_LAYOUT;
            }
            return RECEIVE_LAYOUT;
        }

        @Override
        public void onBindViewHolder(@NonNull ChatFragment.ChatVH holder, int position) {
            holder.text.setText(this.newest.get(position).message);
        }

        @Override
        public int getItemCount() {
            if(newest!=null) {
                return this.newest.size();
            }
            return 0;
        }
    }



    private static class ChatVH extends RecyclerView.ViewHolder{

        View itemView;
        TextView text;

        public ChatVH(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.text = itemView.findViewById(R.id.chat_text_sent_receive);
        }
    }
}
