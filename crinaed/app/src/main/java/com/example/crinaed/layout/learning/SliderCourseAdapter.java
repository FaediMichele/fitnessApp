package com.example.crinaed.layout.learning;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.crinaed.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderCourseAdapter extends SliderViewAdapter<SliderCourseAdapter.SliderCourseVH> {

    private final int nummero_recensioni = 10; //da toglire----------------------------------------------


    private final String idCourse;  //questo lo messo per te non so se ti serve per caricare le immagini ma
                                    // comunque ti faccio arrivare l'id del corso fino a qui se non ti serve
                                    // puoi cancellarlo
    private final Activity context;

    public SliderCourseAdapter(Activity context , String idCourse) {
        this.context = context;
        this.idCourse = idCourse;
    }

    @Override
    public SliderCourseAdapter.SliderCourseVH onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        return new SliderCourseVH(itemView);
    }

    @Override
    public void onBindViewHolder(SliderCourseAdapter.SliderCourseVH viewHolder, int position) {
            viewHolder.imageView.setImageDrawable(context.getDrawable(R.drawable.simple_people));
    }

    @Override
    public int getCount() {
        return nummero_recensioni;
    }

    public class SliderCourseVH extends SliderViewAdapter.ViewHolder{

        ImageView imageView;

        public SliderCourseVH(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_course);
        }
    }
}

