package com.example.crinaed.layout.learning;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.crinaed.R;
import com.example.crinaed.database.entity.join.CourseWithExercise;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderCourseAdapter extends SliderViewAdapter<SliderCourseAdapter.SliderCourseVH> {


    private CourseWithExercise course;
    private final Activity context;

    public SliderCourseAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public SliderCourseAdapter.SliderCourseVH onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        return new SliderCourseVH(itemView);
    }

    @Override
    public void onBindViewHolder(SliderCourseAdapter.SliderCourseVH viewHolder, int position) {
        if(course != null && course.course.imagesDownloaded){
            viewHolder.imageView.setImageURI(Uri.parse(course.course.images[position]));
        }
    }

    @Override
    public int getCount() {
        if(course == null || course.course==null || !course.course.imagesDownloaded){
            return 0;
        }
        return course.course.images.length;
    }

    public void setCourse(CourseWithExercise course) {
        this.course=course;
        notifyDataSetChanged();
    }

    public static class SliderCourseVH extends SliderViewAdapter.ViewHolder{

        ImageView imageView;

        public SliderCourseVH(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_course);
        }
    }
}

