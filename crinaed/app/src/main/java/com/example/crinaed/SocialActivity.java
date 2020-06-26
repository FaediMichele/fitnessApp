package com.example.crinaed;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.ProgressBar.SliderProgressBarModel;
import com.example.crinaed.ProgressBarDetails.ProgressBarDetailsAdapter;

import java.util.ArrayList;
import java.util.List;

public class SocialActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        //delete status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SocialSearchAdapter adapter = new SocialSearchAdapter(ModelloFittizio.getListModel());
        recyclerView.setAdapter(adapter);
    }

    private class SocialSearchAdapter extends RecyclerView.Adapter<SocialSearchViewHolder>{

        private List<ModelloFittizio> modelloFittizio;

        public SocialSearchAdapter(List<ModelloFittizio> modelloFittizio){
            this.modelloFittizio = modelloFittizio;
        }

        @NonNull
        @Override
        public SocialSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_social_search, parent, false);
            return new SocialSearchViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull SocialSearchViewHolder holder, int position) {
            holder.imageView.setImageDrawable(getDrawable(R.drawable.simple_people));
            ModelloFittizio modelloFittizio = this.modelloFittizio.get(position);
            holder.nameLastName.setText(modelloFittizio.nome + " " + modelloFittizio.cognome);
            holder.email.setText(modelloFittizio.email);
            holder.objective.setText(modelloFittizio.titoloObbiettivo);
            holder.step.setText(modelloFittizio.titoloStep);
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
            this.imageView = itemView.findViewById(R.id.image_profile);
            this.nameLastName  = itemView.findViewById(R.id.name_last_name);
            this.email = itemView.findViewById(R.id.email);
            this.objective  = itemView.findViewById(R.id.objective);
            this.step = itemView.findViewById(R.id.step);
        }
    }

    //---------------da qui parte la classe del modello fitizzio che dovra essere sostituita con il db e quindi poi questa classe del modello fittizio verrà eliminata------------------------------------------
    private static class ModelloFittizio {
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

        static public List<ModelloFittizio> getListModel(){
            List<ModelloFittizio> modelloFittizioList = new ArrayList<>();
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            modelloFittizioList.add(new ModelloFittizio("nome","cognome","email@dominio.com","obbiettivo","titolo step"));
            return modelloFittizioList;
        }
    }


}
