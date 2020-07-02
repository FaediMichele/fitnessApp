package com.example.crinaed;

import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatFragment extends Fragment {

    public static final String SOCIAL_KEY_ID = "ID";
    public static final String TAG_IDENTITY = "CHAT_FRAGMENT_TO_IDENTITY_FRAGMENT";
    public static final String SOCIAL_KEY_NAME = "NAME";
    public static final String SOCIAL_KEY_LAST_NAME = "LAST_NAME";
    public static final String SOCIAL_KEY_EMAIL = "EMAIL";
    public static final String SOCIAL_KEY_TITLE_OBJECTIVE = "TITLE_OBJECTIVE";
    public static final String SOCIAL_KEY_TITLE_STEP = "TITLE_STEP";
    public static final String TAG_BACK_STECK = "BACK_STACK_CHAT_FRAGMENT_TO_IDENTITY_FRAGMENT";



    static final public int SENT_LAYOUT = 0;
    static final public int RECEIVE_LAYOUT = 1;

    String id;//questo è l'unico elemento che probabilmente rimarra perchè da questo si possono scaricare tutti gli altri dati
    TextView nameAndLastName;
    TextView lastName;
    ImageView imageProfile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        //delete status bar
        this.nameAndLastName =  view.findViewById(R.id.name_and_last_name);
        this.imageProfile = view.findViewById(R.id.image_profile);
        Bundle dataForChatActivity = getArguments();
        this.imageProfile.setImageDrawable(getActivity().getDrawable(R.drawable.simple_people));
        this.id = dataForChatActivity.getString(ChatActivity.SOCIAL_KEY_ID);
        this.nameAndLastName.setText(dataForChatActivity.getString(SOCIAL_KEY_NAME)+" "+dataForChatActivity.getString(SOCIAL_KEY_LAST_NAME) );
        //set recycler view
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final ChatFragment.ChatAdapter adapter = new ChatFragment.ChatAdapter(ChatFragment.ModelloFittizioMessage.getListModelMessage());
        recyclerView.setAdapter(adapter);
        //set click listener
        view.findViewById(R.id.back_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        final EditText editText = view.findViewById(R.id.text_send);
        view.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatFragment.ModelloFittizioMessage message = new ChatFragment.ModelloFittizioMessage(true,editText.getText().toString(), Calendar.getInstance());
                editText.setText("");
                adapter.addItem(message);
                adapter.notifyItemInserted(adapter.listChatModel.size()-1);
                recyclerView.getLayoutManager().scrollToPosition(adapter.listChatModel.size()-1);
            }
        });

        this.nameAndLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IdentityFragment identityFragment = new IdentityFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container_chat, identityFragment, TAG_IDENTITY);
                transaction.addToBackStack(TAG_BACK_STECK);
                transaction.commit();
            }
        });
        return view;
    }


    private  class ChatAdapter extends RecyclerView.Adapter<ChatFragment.ChatVH>{

        private List<ChatFragment.ModelloFittizioMessage> listChatModel;

        public ChatAdapter(List<ChatFragment.ModelloFittizioMessage> listChatModel) {
            this.listChatModel = listChatModel;//la lista deve essere ordinata dal messaggio meno recente a quello più recente in posizione 0 c'è quello meno recente
        }

        public void addItem(ChatFragment.ModelloFittizioMessage message){
            this.listChatModel.add(message);
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
            return new ChatFragment.ChatVH(itemView);
        }

        @Override
        public int getItemViewType(int position) {
            if(this.listChatModel.get(position).isSent){
                return ChatFragment.SENT_LAYOUT;
            }
            return  RECEIVE_LAYOUT;
        }

        @Override
        public void onBindViewHolder(@NonNull ChatFragment.ChatVH holder, int position) {
            holder.text.setText(this.listChatModel.get(position).textMessage);
        }

        @Override
        public int getItemCount() {
            return this.listChatModel.size();
        }
    }



    private class ChatVH extends RecyclerView.ViewHolder{

        View itemView;
        TextView text;

        public ChatVH(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.text = itemView.findViewById(R.id.chat_text_sent_receive);
        }
    }


    //-----------------------------------modello da eliminare------------------------------------------------------------------------
    public static class ModelloFittizioMessage{
        boolean isSent;
        String textMessage;
        Calendar dateMessage;

        public ModelloFittizioMessage(boolean isSent, String textMessage, Calendar dateMessage) {
            this.isSent = isSent;
            this.textMessage = textMessage;
            this.dateMessage = dateMessage;
        }

        static List<ChatFragment.ModelloFittizioMessage> getListModelMessage(){
            List<ChatFragment.ModelloFittizioMessage> list = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.set(2020,Calendar.MAY,01,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(true, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,01,15,01);
            list.add(new ChatFragment.ModelloFittizioMessage(false, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,01,15,30);
            list.add(new ChatFragment.ModelloFittizioMessage(true, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,01,15,45);
            list.add(new ChatFragment.ModelloFittizioMessage(false, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,01,16,00);
            list.add(new ChatFragment.ModelloFittizioMessage(true, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,02,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(true, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,02,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(false, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,04,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(true, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,05,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(true, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,06,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(false, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,07,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(false, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,8,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(false, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,9,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(true, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,10,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(true, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,11,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(true, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,12,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(true, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,13,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(false, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,14,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(false, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            calendar.set(2020,Calendar.MAY,15,15,00);
            list.add(new ChatFragment.ModelloFittizioMessage(true, "testo prova testo prova testo prova testo prova testo prova testo prova testo prova testo prova ",calendar));
            return list;
        }

        @Override
        public String toString() {
            return "ModelloFittizioMessage{" +
                    "isSent=" + isSent +
                    ", textMessage='" + textMessage + '\'' +
                    ", dateMessage=" + dateMessage +
                    '}';
        }
    }

}
