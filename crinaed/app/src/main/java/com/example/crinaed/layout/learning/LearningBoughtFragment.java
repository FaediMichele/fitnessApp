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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.view.FullScreenVideoActivity;

import java.util.ArrayList;
import java.util.List;

public class LearningBoughtFragment extends Fragment {

    public static final String TAG_LESSON = "LEARNING_BOUGHT_FRAGMENT_TO_LESSON_FRAGMENT";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_bought, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        LearningBoughtAdapter adapter = new LearningBoughtAdapter(ModelloVideoBought.getModello());
        recyclerView.setAdapter(adapter);

        return view;
    }


    private class LearningBoughtAdapter extends RecyclerView.Adapter<LearningBoughtVH>{

        private List<ModelloVideoBought> modelloVideoBoughtList;

        public LearningBoughtAdapter(List<ModelloVideoBought> modelloVideoBoughtList) {
            this.modelloVideoBoughtList = modelloVideoBoughtList;
        }

        @NonNull
        @Override
        public LearningBoughtVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning_bought, parent, false);
            return new LearningBoughtVH(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LearningBoughtVH holder, int position) {
            holder.description.setText(this.modelloVideoBoughtList.get(position).description);
            holder.title.setText(this.modelloVideoBoughtList.get(position).title);
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
            return modelloVideoBoughtList.size();
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
    private static class ModelloVideoBought {
        String id;
        String title;
        String description;
        Image image;

        public ModelloVideoBought(String id, String title, String description, Image image) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.image = image;
        }

        public static List<ModelloVideoBought> getModello (){
            List<ModelloVideoBought> modelloList = new ArrayList<>();
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            modelloList.add(new ModelloVideoBought("id","titolo della lezione","descrizione breve del corso descrizione breve del corso descrizione breve del corso descrizione breve del corso",null));
            return modelloList;
        }
    }
}
