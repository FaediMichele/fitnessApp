package com.example.crinaed.layout.learning;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;

import java.util.ArrayList;
import java.util.List;

public class LearningBuySearchFragment extends Fragment  {
    public static final String TAG_SOCIAL_ARCHIVE = "SOCIAL_FRAGMENT_TO_SOCIAL_ARCHIVE_FRAGMENT";
    public static final String TAG_DETAIL = "LEARNING_BUY_FRAGMENT_TO_LEARNING_BUY_DETAIL_FRAGMENT";
    final public static int TYPE_VIEW_ITEM_VIEW_ARCHIVE = 0;
    final public static int TYPE_VIEW_VIEW_NORMAL = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_buy_search, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        LearningBuySearchFragment.LearningAdapter adapter = new LearningBuySearchFragment.LearningAdapter(LearningBuySearchFragment.ModelloCourseBuy.getListModel());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private class LearningAdapter extends RecyclerView.Adapter<LearningBuySearchFragment.LearningViewHolder>{

        private List<LearningBuySearchFragment.ModelloCourseBuy> modelloFittizio;

        public LearningAdapter(List<LearningBuySearchFragment.ModelloCourseBuy> modelloFittizio){
            this.modelloFittizio = modelloFittizio;
        }

        @NonNull
        @Override
        public LearningBuySearchFragment.LearningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning, parent, false);
            return new LearningBuySearchFragment.LearningViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LearningBuySearchFragment.LearningViewHolder holder, int position) {
        ModelloCourseBuy.TypeCourse typeCourse = this.modelloFittizio.get(position).typeCourse;
        switch (typeCourse){
            case SOCIAL:
                holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.course_social));
                break;
            case LEARNING:
                holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.course_learning));
                break;
            case PHYSICAL:
                holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.course_physical));
                break;
        }
        holder.imageView.setImageDrawable(getActivity().getDrawable(R.drawable.simple_people));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    LearningBuyDetailsFragment learningBuyDetailsFragment = new LearningBuyDetailsFragment();
//                    //learningBuyDetailsFragment.setArguments(dataForChatActivity);
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.container_learning, learningBuyDetailsFragment, TAG_DETAIL);
//                    //transaction.addToBackStack(TAG_BACK_STECK);
//                    transaction.commit();

                    //da eliminare
                    LearningBoughtFragment learningBuyDetailsFragment = new LearningBoughtFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container_learning, learningBuyDetailsFragment, TAG_DETAIL);
                    transaction.addToBackStack(null);
                    transaction.commit();
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
    public static class ModelloCourseBuy {
        String id;
        String title;
        Image image;
        int price;
        TypeCourse typeCourse;

        public ModelloCourseBuy(String id, String titleCourse, Image imageCourse, int price, TypeCourse typeCourse ) {
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

        static public List<ModelloCourseBuy> getListModel(){
            List<ModelloCourseBuy> modelloFittizioList = new ArrayList<>();
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourseBuy("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            return modelloFittizioList;
        }

        enum TypeCourse{
            PHYSICAL,LEARNING,SOCIAL
        }

        public static class Review {
            String id;
            int value; //da 1 a 5
            String firstName;
            String lastName;
            String title;
            String description;

            public Review(String id, int value, String firstName, String lastName, String title, String description) {
                this.id = id;
                this.value = value;
                this.firstName = firstName;
                this.lastName = lastName;
                this.title = title;
                this.description = description;
            }

            static public List<Review> getListModel() {
                List<Review> reviewList = new ArrayList<>();
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;
                reviewList.add(new Review("id_review",3,"Nome","Cognome","Titolo Recensione","descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione descrizione della recensione ")) ;

                return reviewList;
            }
        }
    }
}
