package com.example.crinaed.layout.learning;

import android.view.View;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderCourseAdapter extends SliderViewAdapter<SliderCourseAdapter.SliderCourseVH> {
    @Override
    public SliderCourseAdapter.SliderCourseVH onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(SliderCourseAdapter.SliderCourseVH viewHolder, int position) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    public class SliderCourseVH extends SliderViewAdapter.ViewHolder{

        public SliderCourseVH(View itemView) {
            super(itemView);
        }
    }
}

