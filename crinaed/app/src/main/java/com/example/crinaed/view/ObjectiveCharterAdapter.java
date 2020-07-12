package com.example.crinaed.view;

import android.view.View;
import android.view.ViewGroup;

import com.example.crinaed.ProgressBar.SliderProgressBarAdapterNew;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class ObjectiveCharterAdapter extends SliderViewAdapter<ObjectiveCharterAdapter.ObjectiveCharterVH> {

    @Override
    public ObjectiveCharterVH onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(ObjectiveCharterVH viewHolder, int position) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    public class ObjectiveCharterVH extends SliderViewAdapter.ViewHolder {
        public ObjectiveCharterVH(View itemView) {
            super(itemView);
        }
    }
}
