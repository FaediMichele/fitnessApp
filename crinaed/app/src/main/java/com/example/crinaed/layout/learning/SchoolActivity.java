package com.example.crinaed.layout.learning;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
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
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.join.CourseWithExercise;
import com.example.crinaed.database.entity.join.SchoolData;
import com.example.crinaed.util.Category;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class SchoolActivity  extends AppCompatActivity {
    public final static String TAG_ID_SCHOOL="TAG_ID_SCHOOL";
    public final static String TAG_ID_COURSE_FROM="TAG_ID_COURSE_FROM";
    private long idCourseFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);

        final TextView title = findViewById(R.id.title_school);
        final ImageView imageView = findViewById(R.id.image_school);
        final TextView reviewValue = findViewById(R.id.review_value);
        final ShapeableImageView imageProfileFounder = findViewById(R.id.image_profile_founder);
        final TextView nameAndLastName = findViewById(R.id.name_and_last_name);
        final TextView email = findViewById(R.id.email);
        final TextView descriptionFounder = findViewById(R.id.description_founder);
        final TextView descriptionSchool = findViewById(R.id.description_school);
        final SchoolActivity.CourseAdapter adapterReview = new SchoolActivity.CourseAdapter();

        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
            if(bundle.containsKey(TAG_ID_COURSE_FROM)){
                idCourseFrom=bundle.getLong(TAG_ID_COURSE_FROM);
            }
             if(bundle.containsKey(TAG_ID_SCHOOL)){
                long idSchool = bundle.getLong(TAG_ID_SCHOOL);
                DatabaseUtil.getInstance().getRepositoryManager().getSchoolRepository().getSchoolById(idSchool).observe(this, new Observer<SchoolData>() {
                    @Override
                    public void onChanged(SchoolData schoolData) {
                        title.setText(schoolData.school.name);
                        if(schoolData.school.imageDownloaded){
                            imageView.setImageURI(Uri.parse(schoolData.school.image));
                        }
                        if(schoolData.user.imageDownloaded){
                            imageProfileFounder.setImageURI(Uri.parse(schoolData.user.image));
                        }
                        nameAndLastName.setText(getString(R.string.name_surname, schoolData.user.firstname, schoolData.user.surname));
                        email.setText(schoolData.user.email);
                        descriptionFounder.setText(schoolData.school.desc);
                        descriptionSchool.setText(schoolData.school.desc);
                        double sum=0;
                        for(CourseWithExercise c : schoolData.courseData){
                            sum+=c.course.review;
                        }
                        reviewValue.setText(getString(R.string.review_val, (sum/schoolData.courseData.size())));
                        adapterReview.setCourse(schoolData.courseData);
                    }
                });
            }
        }

        //manager RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterReview);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class CourseAdapter extends RecyclerView.Adapter<SchoolActivity.LearningViewHolder>{

        private List<CourseWithExercise> courses;

        @NonNull
        @Override
        public SchoolActivity.LearningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning, parent, false);
            return new LearningViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull SchoolActivity.LearningViewHolder holder, final int position) {
            final Course course = this.courses.get(position).course;
            switch (course.cat){
                case SOCIAL:
                    holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.course_social));
                    break;
                case MENTAL:
                    holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.course_learning));
                    break;
                case SPORT:
                    holder.itemView.findViewById(R.id.course_item).setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.course_physical));
                    break;
            }
            if(course.imagesDownloaded){
                holder.imageView.setImageURI(Uri.parse(course.images[0]));

            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //start acrivity learning fragment del corso gia acquistato
                    if(idCourseFrom == course.idCourse){
                        /* TODO bad animation here */
                        onBackPressed();
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putLong(LearningBuySearchFragment.KEY_ID_COURSE, course.idCourse);
                    bundle.putBoolean(LearningActivity.KEY_START_PAGER,true);
                    Intent learningIntent = new Intent(getApplicationContext(), LearningActivity.class);
                    learningIntent.putExtras(bundle);
                    startActivity(learningIntent);
                }
            });
            holder.description.setText(course.desc);
            holder.title.setText(course.name);
        }


        @Override
        public int getItemCount() {
            if(courses!=null){
                return courses.size();
            }
            return 0;
        }

        public void setCourse(List<CourseWithExercise> courseData) {
            this.courses=courseData;
        }
    }

    private static class LearningViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;
        TextView description;

        public LearningViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_course);
            this.title = itemView.findViewById(R.id.title_course);
            this.description = itemView.findViewById(R.id.description_course);
        }
    }
}