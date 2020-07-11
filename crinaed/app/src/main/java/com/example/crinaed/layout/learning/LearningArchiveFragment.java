package com.example.crinaed.layout.learning;

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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.join.CourseWithExercise;

import java.util.ArrayList;
import java.util.List;

public class LearningArchiveFragment extends Fragment {
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
        LearningArchiveFragment.LearningAdapter adapter = new LearningArchiveFragment.LearningAdapter(this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private class LearningAdapter extends RecyclerView.Adapter<LearningArchiveFragment.LearningViewHolder>{

        private List<CourseWithExercise> course;

        public LearningAdapter(LifecycleOwner owner){
            DatabaseUtil.getInstance().getRepositoryManager().getSchoolRepository().getCourse(true).observe(owner, new Observer<List<CourseWithExercise>>() {
                @Override
                public void onChanged(List<CourseWithExercise> courseWithExercises) {
                    course=courseWithExercises;
                    notifyDataSetChanged();
                }
            });
        }

        @NonNull
        @Override
        public LearningArchiveFragment.LearningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning, parent, false);
            return new LearningViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LearningArchiveFragment.LearningViewHolder holder, final int position) {
                final CourseWithExercise course = this.course.get(position);
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
                holder.title.setText(course.course.name);
                holder.school.setText(course.school.name);
                holder.description.setText(course.course.desc);
                holder.review.setText(getString(R.string.review_val, course.course.review));
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

        }


        @Override
        public int getItemCount() {
            if(course!=null){
                return course.size();
            }
            return 0;
        }
    }

    private static class LearningViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;
        TextView description;
        TextView review;
        TextView school;

        public LearningViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_course);
            this.title = itemView.findViewById(R.id.title_course);
            this.description = itemView.findViewById(R.id.description_course);
            review = itemView.findViewById(R.id.value_review);
            school = itemView.findViewById(R.id.school);
        }
    }
}