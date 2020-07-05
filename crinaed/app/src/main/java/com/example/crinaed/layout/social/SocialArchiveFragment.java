package com.example.crinaed.layout.social;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.layout.social.chat.ChatActivity;
import com.example.crinaed.R;

import java.util.ArrayList;
import java.util.List;

public class SocialArchiveFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social_archive, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        SocialArchiveFragment.SocialSearchAdapter adapter = new SocialArchiveFragment.SocialSearchAdapter(SocialArchiveFragment.ModelloFittizio.getListModel());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private class SocialSearchAdapter extends RecyclerView.Adapter<SocialArchiveFragment.SocialSearchViewHolder>{

        private List<SocialArchiveFragment.ModelloFittizio> modelloFittizio;

        public SocialSearchAdapter(List<SocialArchiveFragment.ModelloFittizio> modelloFittizio){
            this.modelloFittizio = modelloFittizio;
        }

        @NonNull
        @Override
        public SocialArchiveFragment.SocialSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_social_search, parent, false);
            return new SocialArchiveFragment.SocialSearchViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull SocialArchiveFragment.SocialSearchViewHolder holder, int position) {
            holder.imageView.setImageDrawable(getActivity().getDrawable(R.drawable.simple_people));
            final SocialArchiveFragment.ModelloFittizio modelloFittizio = this.modelloFittizio.get(position);
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

        public SocialSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_video);
            this.nameLastName = itemView.findViewById(R.id.name_last_name);
            this.email = itemView.findViewById(R.id.email);
            this.objective = itemView.findViewById(R.id.objective);
            this.step = itemView.findViewById(R.id.step);
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

        static public List<SocialArchiveFragment.ModelloFittizio> getListModel(){
            List<SocialArchiveFragment.ModelloFittizio> modelloFittizioList = new ArrayList<>();
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new SocialArchiveFragment.ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            return modelloFittizioList;
        }
    }

}
