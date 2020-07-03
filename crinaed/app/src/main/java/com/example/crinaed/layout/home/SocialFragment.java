package com.example.crinaed.layout.home;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.layout.social.chat.ChatActivity;
import com.example.crinaed.R;
import com.example.crinaed.layout.social.SocialArchiveFragment;

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
        SocialFragment.SocialSearchAdapter adapter = new SocialFragment.SocialSearchAdapter(SocialFragment.ModelloFittizio.getListModel());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private class SocialSearchAdapter extends RecyclerView.Adapter<SocialFragment.SocialSearchViewHolder>{

        private List<SocialFragment.ModelloFittizio> modelloFittizio;

        public SocialSearchAdapter(List<SocialFragment.ModelloFittizio> modelloFittizio){
            this.modelloFittizio = modelloFittizio;
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
                holder.imageView.setImageDrawable(getActivity().getDrawable(R.drawable.simple_people));
                final SocialFragment.ModelloFittizio modelloFittizio = this.modelloFittizio.get(position);
                holder.nameLastName.setText(modelloFittizio.nome + " " + modelloFittizio.cognome);
                holder.email.setText(modelloFittizio.email);
                holder.objective.setText(modelloFittizio.titoloObbiettivo);
                holder.step.setText(modelloFittizio.titoloStep);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ChatActivity.SOCIAL_KEY_ID, modelloFittizio.id);
                        bundle.putString(ChatActivity.SOCIAL_KEY_NAME, modelloFittizio.nome);
                        bundle.putString(ChatActivity.SOCIAL_KEY_LAST_NAME, modelloFittizio.cognome);
                        bundle.putString(ChatActivity.SOCIAL_KEY_EMAIL, modelloFittizio.email);
                        bundle.putString(ChatActivity.SOCIAL_KEY_TITLE_OBJECTIVE, modelloFittizio.titoloObbiettivo);
                        bundle.putString(ChatActivity.SOCIAL_KEY_TITLE_STEP, modelloFittizio.nome);
                        Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                        chatIntent.putExtras(bundle);
                        startActivity(chatIntent);
                    }
                });
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
            return this.modelloFittizio.size();
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

    //---------------da qui parte la classe del modello fitizzio che dovra essere sostituita con il db e quindi poi questa classe del modello fittizio verrà eliminata------------------------------------------
    public static class ModelloFittizio {
        String id;
        String nome;
        String cognome;
        String email;
        String titoloObbiettivo;
        String titoloStep;

        @Override
        public String toString() {
            return "ModelloFittizio{" +
                    "nome='" + nome + '\'' +
                    ", cognome='" + cognome + '\'' +
                    ", email='" + email + '\'' +
                    ", titoloObbiettivo='" + titoloObbiettivo + '\'' +
                    ", titoloStep='" + titoloStep + '\'' +
                    ", image=" + image +
                    '}';
        }

        Image image; // bisognerebbe anche avere l'imagine profilo del utente da visualizare, e se no cel'ha crearne una randomica e assegnarliela alla creazione

        public ModelloFittizio(String nome, String cognome, String email, String titoloObbiettivo, String titoloStep) {
            this.nome = nome;
            this.cognome = cognome;
            this.email = email;
            this.titoloObbiettivo = titoloObbiettivo;//titolo o descrizione
            this.titoloStep = titoloStep;
        }

        static public List<SocialFragment.ModelloFittizio> getListModel(){
            List<SocialFragment.ModelloFittizio> modelloFittizioList = new ArrayList<>();
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            return modelloFittizioList;
        }
    }

}
