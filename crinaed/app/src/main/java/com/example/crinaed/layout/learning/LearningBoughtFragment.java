package com.example.crinaed.layout.learning;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.join.CourseWithExercise;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LearningBoughtFragment extends Fragment {

    public static final String TAG_LESSON = "LEARNING_BOUGHT_FRAGMENT_TO_LESSON_FRAGMENT";
    private String idCourse;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_bought, container, false);

        //to receive model
        Bundle dataLearning = getArguments();
        if(dataLearning == null){
            Snackbar.make(view, R.string.unknown_error, BaseTransientBottomBar.LENGTH_LONG).show();
            return view;
        }
        long idCourse = dataLearning.getLong(LearningBuySearchFragment.KEY_ID_COURSE);


        //set view
        final TextView title = view.findViewById(R.id.title_course);
        final LessonAdapter adapter = new LessonAdapter();

        DatabaseUtil.getInstance().getRepositoryManager().getSchoolRepository().getCourseById(idCourse).observe(this, new Observer<CourseWithExercise>() {
            @Override
            public void onChanged(CourseWithExercise course) {
                adapter.setCourse(course);
                title.setText(course.course.name);
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        return view;
    }


    private class LessonAdapter extends RecyclerView.Adapter<LearningBoughtVH>{

        private CourseWithExercise course;
        public LessonAdapter() {
        }

        public void setCourse(CourseWithExercise course){
            this.course=course;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public LearningBoughtVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
            return new LearningBoughtVH(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LearningBoughtVH holder, final int position) {
            holder.description.setText(this.course.exercises.get(position).desc);
            holder.title.setText(this.course.exercises.get(position).name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), LessonActivity.class);
                    intent.putExtra(LessonActivity.ID_LESSON, course.exercises.get(position).idExercise);
                    ((Activity) Objects.requireNonNull(getContext())).startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            if(course != null){
                return course.exercises.size();
            }
            return 0;
        }
    }

    private static class LearningBoughtVH extends RecyclerView.ViewHolder{

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
}
