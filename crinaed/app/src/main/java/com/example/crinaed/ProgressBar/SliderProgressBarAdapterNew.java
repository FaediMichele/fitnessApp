package com.example.crinaed.ProgressBar;


import android.view.View;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.SliderViewAdapter;

/**
 * This class manage the categories and the relatives Dialog.
 */
public class SliderProgressBarAdapterNew extends SliderViewAdapter<SliderProgressBarAdapterNew.SliderProgressBarVH> {


    @Override
    public SliderProgressBarVH onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(SliderProgressBarVH viewHolder, int position) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    static class SliderProgressBarVH extends SliderViewAdapter.ViewHolder {

        public SliderProgressBarVH(View itemView) {
            super(itemView);
        }
    }
}
