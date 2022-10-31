package com.example.crinaed.layout.social.chat;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.database.entity.join.user.UserWithUser;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;

import java.util.Objects;

public class IdentityFragment extends Fragment {

    public static final String TAG_CHAT = "IDENTITY_FRAGMENT_TO_CHAT_FRAGMENT";
    public static final String TAG_BACK_STECK = "BACK_STACK_IDENTITY_FRAGMENT_TO_CHAT_FRAGMENT";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identity, container, false);

        final Bundle dataForChatActivity = getArguments();
        if(dataForChatActivity==null || !dataForChatActivity.containsKey(ChatActivity.TAG_KEY_FRIENDSHIP)){
            Objects.requireNonNull(getActivity()).onBackPressed();
            return view;
        }
        String idFriendship_string =dataForChatActivity.getString(ChatActivity.TAG_KEY_FRIENDSHIP);
        String name =dataForChatActivity.getString(ChatActivity.SOCIAL_KEY_NAME);
        String lastName = dataForChatActivity.getString(ChatActivity.SOCIAL_KEY_LAST_NAME);
        if(name==null || lastName ==null || idFriendship_string == null){
            Objects.requireNonNull(getActivity()).onBackPressed();
            return view;
        }
        final long idFriendship = Long.parseLong(idFriendship_string);
        name=name.substring(0,1).toUpperCase()+name.substring(1);
        lastName=lastName.substring(0,1).toUpperCase()+lastName.substring(1);
        TextView nameView = view.findViewById(R.id.title_details);
        nameView.setText(getString(R.string.name_surname, name,lastName));
        TextView email = view.findViewById(R.id.email);
        email.setText(dataForChatActivity.getString(ChatActivity.SOCIAL_KEY_EMAIL));
        view.findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        String imagePath = dataForChatActivity.getString(ChatActivity.SOCIAL_KEY_IMAGE_PATH);
        if(imagePath!=null){
            ImageView image = view.findViewById(R.id.image_profile);
            image.setImageURI(Uri.parse(imagePath));
        }
        final Button button = view.findViewById(R.id.block);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String tmp = dataForChatActivity.getString(ChatActivity.SOCIAL_KEY_ID);
                    if(tmp != null) {
                        ServerManager.getInstance(getContext()).blockUser(Long.parseLong(tmp), new Lambda() {
                            @Override
                            public Object[] run(Object... paramether) {
                                Toast.makeText(getContext(), getString(R.string.block_ok), Toast.LENGTH_SHORT).show();
                                return null;
                            }
                        }, new Lambda() {
                            @Override
                            public Object[] run(Object... paramether) {
                                Toast.makeText(getContext(), getString(R.string.connection_error, getString(R.string.block_error)), Toast.LENGTH_LONG).show();
                                return null;
                            }
                        });
                    }
            }
        });

        DatabaseUtil.getInstance().getRepositoryManager().getFriendRepository().getFriendshipByFriendship(idFriendship).observe(this, new Observer<UserWithUser>() {
            @Override
            public void onChanged(UserWithUser userWithUser) {
                if(userWithUser.friendship.blocked== Util.getInstance().getIdUser()){
                    button.setText(R.string.unblock);
                } else{
                    button.setText(R.string.block_friend);
                }
            }
        });

        return view;
    }
}
