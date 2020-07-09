package com.example.crinaed.layout.learning;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class SchoolActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);

        //costruzione della view modello---------------------------------------------------------------------------
        TextView title = findViewById(R.id.title_school);
        ImageView imageView = findViewById(R.id.image_school);
        TextView reviewValue = findViewById(R.id.review_value);
        ShapeableImageView imageProfileFounder = findViewById(R.id.image_profile_founder);
        TextView nameAndLastName = findViewById(R.id.name_and_last_name);
        TextView email = findViewById(R.id.email);
        TextView descriptionFounder = findViewById(R.id.description_founder);
        TextView descriptionSchool = findViewById(R.id.description_school);

        title.setText("Come non imparare mai nulla");
        imageView.setImageDrawable(this.getDrawable(R.drawable.simple_corso));
        imageProfileFounder.setImageDrawable(this.getDrawable(R.drawable.simple_people));
        nameAndLastName.setText("Nome secondoNome Congnome TerzoCongnome");
        email.setText("email@emaildidomeinio.onmicrosoft.com");
        descriptionFounder.setText("Sono il fondatore di una scuola che la meta basta ma oltre a questo" +
                "nalla vita non faccio altro non so neanche quello che ho fatto nei miei corsi ma ora mai" +
                "ha poca importanza dato che aquisico clienti a catinate");
        descriptionSchool.setText("questa scuola è stata fondata da uno dei personaggi migliori della storia il suo nome è" +
                "singorina giertolda e questo basta per giustificarne la bonta degli insegnamenti tutti i nostri corsisti non " +
                "hanno mai ottenutto nessuno risultato i nessun campo quindi se vuoi unirti anche te fai pure");
        reviewValue.setText("3,5/5");

        //manager RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SchoolActivity.CourseAdapter adapterReview = new SchoolActivity.CourseAdapter(ModelloCourse.getListModel());
        recyclerView.setAdapter(adapterReview);
    }

    private class CourseAdapter extends RecyclerView.Adapter<SchoolActivity.LearningViewHolder>{

        private List<SchoolActivity.ModelloCourse> modelloFittizio;

        public CourseAdapter(List<SchoolActivity.ModelloCourse> modelloFittizio){
            this.modelloFittizio = modelloFittizio;
        }

        @NonNull
        @Override
        public SchoolActivity.LearningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning, parent, false);
            return new SchoolActivity.LearningViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull SchoolActivity.LearningViewHolder holder, final int position) {
            SchoolActivity.ModelloCourse.TypeCourse typeCourse = this.modelloFittizio.get(position).typeCourse;
            switch (typeCourse){
                case SOCIAL:
                    holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.course_social));
                    break;
                case LEARNING:
                    holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.course_learning));
                    break;
                case PHYSICAL:
                    holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.course_physical));
                    break;
            }
            holder.imageView.setImageDrawable(getApplicationContext().getDrawable(R.drawable.simple_people));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //start acrivity learning fragment del corso gia acquistato
                    Bundle bundle = new Bundle();
                    bundle.putString(LearningBuySearchFragment.KEY_ID_COURSE,modelloFittizio.get(position).id);
                    bundle.putBoolean(LearningActivity.KEY_START_PAGER,true);
                    Intent learningIntent = new Intent(getApplicationContext(), LearningActivity.class);
                    learningIntent.putExtras(bundle);
                    startActivity(learningIntent);
                }
            });

        }


        @Override
        public int getItemCount() {
            return this.modelloFittizio.size();
        }
    }

    private class LearningViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;
        TextView description;

        public LearningViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_course);
            this.title = itemView.findViewById(R.id.title_lesson);
            this.description = itemView.findViewById(R.id.description_course);
        }
    }


    //---------------da qui parte la classe del modello fitizzio che dovra essere sostituita con il db e quindi poi questa classe del modello fittizio verrà eliminata------------------------------------------
    public static class ModelloCourse {
        String id;
        String title;
        Image image;
        int price;
        SchoolActivity.ModelloCourse.TypeCourse typeCourse;

        public ModelloCourse(String id, String titleCourse, Image imageCourse, int price, SchoolActivity.ModelloCourse.TypeCourse typeCourse ) {
            this.id = id;
            this.title = titleCourse;
            this.image = imageCourse;
            this.price = price;
            this.typeCourse = typeCourse;

        }

        @Override
        public String toString() {
            return "ModelloFittizio{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", image=" + image +
                    ", price=" + price +
                    '}';
        }

        static public List<SchoolActivity.ModelloCourse> getListModel(){
            List<SchoolActivity.ModelloCourse> modelloFittizioList = new ArrayList<>();
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.LEARNING));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.PHYSICAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.SOCIAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.PHYSICAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.SOCIAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.LEARNING));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.PHYSICAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.SOCIAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.LEARNING));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.SOCIAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.LEARNING));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.SOCIAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.PHYSICAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.PHYSICAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.PHYSICAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.SOCIAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.LEARNING));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.LEARNING));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.PHYSICAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.SOCIAL));
            modelloFittizioList.add(new SchoolActivity.ModelloCourse("id_corso","titolo corso",null,1000, SchoolActivity.ModelloCourse.TypeCourse.LEARNING));
            return modelloFittizioList;
        }

        enum   TypeCourse{
            PHYSICAL,LEARNING,SOCIAL
        }
    }
}