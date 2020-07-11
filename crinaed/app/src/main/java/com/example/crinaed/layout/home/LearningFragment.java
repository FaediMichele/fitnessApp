package com.example.crinaed.layout.home;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.join.CourseWithExercise;
import com.example.crinaed.layout.learning.LearningActivity;
import com.example.crinaed.layout.learning.LearningArchiveFragment;
import com.example.crinaed.layout.learning.LearningBuySearchFragment;
import com.example.crinaed.layout.social.SocialArchiveFragment;
import com.example.crinaed.util.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LearningFragment extends Fragment {
    public static final String TAG_LEARNING_ARCHIVE = "LEARNING_FRAGMENT_TO_LEARNING_ARCHIVE_FRAGMENT";
    final public static int TYPE_VIEW_ITEM_VIEW_ARCHIVE = 0;
    final public static int TYPE_VIEW_VIEW_NORMAL = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        LearningFragment.LearningAdapter adapter = new LearningFragment.LearningAdapter(this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private class LearningAdapter extends RecyclerView.Adapter<LearningFragment.LearningViewHolder>{

        private List<CourseWithExercise> newest;

        public LearningAdapter(LifecycleOwner owner){
            DatabaseUtil.getInstance().getRepositoryManager().getSchoolRepository().getCourse(false).observe(owner, new Observer<List<CourseWithExercise>>() {
                @Override
                public void onChanged(List<CourseWithExercise> courseWithExercises) {
                    newest = courseWithExercises;
                    notifyDataSetChanged();
                }
            });
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
            return new LearningViewHolder(itemView, viewType == TYPE_VIEW_ITEM_VIEW_ARCHIVE);
        }

        @Override
        public void onBindViewHolder(@NonNull LearningFragment.LearningViewHolder holder, final int position) {
            if(position > 0) {
                final CourseWithExercise course = newest.get(position-1);
                holder.title.setText(course.course.name);
                holder.description.setText(course.course.desc);
                holder.school.setText(course.school.name);
                holder.review.setText(Objects.requireNonNull(getContext()).getString(R.string.review_val, course.course.review));
                switch (course.course.cat){
                    case SOCIAL:
                        holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.course_social));
                        break;
                    case MENTAL:
                        holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.course_learning));
                        break;
                    case SPORT:
                        holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.course_physical));
                        break;

                }
                if(course.course.imagesDownloaded){
                    holder.imageView.setImageURI(Uri.parse(course.course.images[0]));
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //start acrivity learning fragment del corso gia acquistato
                        Bundle bundle = new Bundle();
                        bundle.putLong(LearningBuySearchFragment.KEY_ID_COURSE, course.course.idCourse);
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
                        LearningArchiveFragment learningArchiveFragment = new LearningArchiveFragment();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, learningArchiveFragment, TAG_LEARNING_ARCHIVE);
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
            if(newest!=null){
                return newest.size()+1;
            }
            return 1;
        }
    }

    private static class LearningViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;
        TextView description;
        TextView review;
        TextView school;

        public LearningViewHolder(@NonNull View itemView, boolean isArchive) {
            super(itemView);
            if(!isArchive){
                this.imageView = itemView.findViewById(R.id.image_course);
                this.title = itemView.findViewById(R.id.title_course);
                this.description = itemView.findViewById(R.id.description_course);
                review = itemView.findViewById(R.id.value_review);
                school = itemView.findViewById(R.id.school);
            }
        }
    }
}
