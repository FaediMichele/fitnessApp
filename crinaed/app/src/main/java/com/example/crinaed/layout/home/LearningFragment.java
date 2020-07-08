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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.layout.learning.LearningActivity;
import com.example.crinaed.layout.learning.LearningBuySearchFragment;
import com.example.crinaed.layout.social.SocialArchiveFragment;

import java.util.ArrayList;
import java.util.List;

public class LearningFragment extends Fragment {
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
        LearningFragment.LearningAdapter adapter = new LearningFragment.LearningAdapter(LearningFragment.ModelloCourse.getListModel());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private class LearningAdapter extends RecyclerView.Adapter<LearningFragment.LearningViewHolder>{

        private List<LearningFragment.ModelloCourse> modelloFittizio;

        public LearningAdapter(List<LearningFragment.ModelloCourse> modelloFittizio){
            this.modelloFittizio = modelloFittizio;
        }

        @NonNull
        @Override
        public LearningFragment.LearningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView;
            switch(viewType){
                case TYPE_VIEW_ITEM_VIEW_ARCHIVE:
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning_archive, parent, false);
                    break;
                case TYPE_VIEW_VIEW_NORMAL:
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning, parent, false);
                    break;
                default:
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning, parent, false);
                    break;
            }
            return new LearningFragment.LearningViewHolder(itemView, viewType == TYPE_VIEW_ITEM_VIEW_ARCHIVE);
        }

        @Override
        public void onBindViewHolder(@NonNull LearningFragment.LearningViewHolder holder, final int position) {
            if(position > 0) {
                ModelloCourse.TypeCourse typeCourse = this.modelloFittizio.get(position).typeCourse;
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
                        //start acrivity learning fragment del corso gia acquistato
                        Bundle bundle = new Bundle();
                        bundle.putString(LearningBuySearchFragment.KEY_ID_COURSE,modelloFittizio.get(position).id);
                        bundle.putBoolean(LearningActivity.KEY_START_PAGER,true);
                        Intent learningIntent = new Intent(getContext(), LearningActivity.class);
                        learningIntent.putExtras(bundle);
                        startActivity(learningIntent);
                    }
                });
            }else{
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //start archivio learning
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
            return position == 0 ? LearningFragment.TYPE_VIEW_ITEM_VIEW_ARCHIVE : LearningFragment.TYPE_VIEW_VIEW_NORMAL;
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

        public LearningViewHolder(@NonNull View itemView, boolean isArchive) {
            super(itemView);
            if(!isArchive){
                this.imageView = itemView.findViewById(R.id.image_course);
                this.title = itemView.findViewById(R.id.title_lesson);
                this.description = itemView.findViewById(R.id.description_course);
            }
        }
    }

    //---------------da qui parte la classe del modello fitizzio che dovra essere sostituita con il db e quindi poi questa classe del modello fittizio verrà eliminata------------------------------------------
    public static class ModelloCourse {
        String id;
        String title;
        Image image;
        int price;
        TypeCourse typeCourse;

        public ModelloCourse(String id, String titleCourse, Image imageCourse, int price, TypeCourse typeCourse ) {
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

        static public List<ModelloCourse> getListModel(){
            List<LearningFragment.ModelloCourse> modelloFittizioList = new ArrayList<>();
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.PHYSICAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.SOCIAL));
            modelloFittizioList.add(new ModelloCourse("id_corso","titolo corso",null,1000,TypeCourse.LEARNING));
            return modelloFittizioList;
        }

        enum   TypeCourse{
            PHYSICAL,LEARNING,SOCIAL
        }
    }
}
