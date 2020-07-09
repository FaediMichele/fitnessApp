package com.example.crinaed.layout.learning;

import android.app.Activity;
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

import com.example.crinaed.R;

import java.util.ArrayList;
import java.util.List;

public class LearningBoughtFragment extends Fragment {

    public static final String TAG_LESSON = "LEARNING_BOUGHT_FRAGMENT_TO_LESSON_FRAGMENT";
    private String idCourse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_bought, container, false);

        //to receive model
        Bundle dataLearning = getArguments();
        idCourse = dataLearning.getString(LearningBuySearchFragment.KEY_ID_COURSE);

        //set view
        TextView title = view.findViewById(R.id.title_course);
        title.setText("Titolo del corso scritto in Java");

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        LessonAdapter adapter = new LessonAdapter(ModelloVideoLessonBought.getModello());
        recyclerView.setAdapter(adapter);

        return view;
    }


    private class LessonAdapter extends RecyclerView.Adapter<LearningBoughtVH>{

        private List<ModelloVideoLessonBought> modelloVideoLessonBoughtList;
        public LessonAdapter(List<ModelloVideoLessonBought> modelloVideoLessonBoughtList) {
            this.modelloVideoLessonBoughtList = modelloVideoLessonBoughtList;
        }

        @NonNull
        @Override
        public LearningBoughtVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
            return new LearningBoughtVH(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LearningBoughtVH holder, int position) {
            holder.description.setText(this.modelloVideoLessonBoughtList.get(position).description);
            holder.title.setText(this.modelloVideoLessonBoughtList.get(position).title);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), LessonActivity.class);
                    ((Activity)getContext()).startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return modelloVideoLessonBoughtList.size();
        }
    }

    private class LearningBoughtVH extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        ImageView image;

        public LearningBoughtVH(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title_lesson);
            this.description = itemView.findViewById(R.id.description_course);
            this.image = itemView.findViewById(R.id.image_video);
        }
    }

//da qui in poi da modello da eliminare----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private static class ModelloVideoLessonBought {
        String id;
        String title;
        String description;
        Image image;

        public ModelloVideoLessonBought(String id, String title, String description, Image image) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.image = image;
        }

        public static List<ModelloVideoLessonBought> getModello (){
            List<ModelloVideoLessonBought> modelloList = new ArrayList<>();
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoLessonBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            return modelloList;
        }
    }
}
