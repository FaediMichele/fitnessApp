package com.example.crinaed.layout.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.crinaed.ProgressBar.SliderProgressBar;
import com.example.crinaed.ProgressBar.SliderProgressBarAdapter;
import com.example.crinaed.ProgressBar.SliderProgressBarAdapterNew;
import com.example.crinaed.ProgressBar.SliderProgressBarNew;
import com.example.crinaed.R;
import com.example.crinaed.util.Period;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class ObjectiveFragment  extends Fragment {

     SliderProgressBar sliderView;
     SliderProgressBarAdapter adapter;

    SliderProgressBarNew sliderViewNew;
    SliderProgressBarAdapterNew adapterNew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_objective, container, false);

        sliderViewNew = view.findViewById(R.id.progress_bar_slider);
        adapterNew = new SliderProgressBarAdapterNew(getContext());
        sliderViewNew.setSliderAdapter(adapterNew);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM
        // or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN
        // or SLIDE and SWAP!!
        sliderViewNew.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        sliderViewNew.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderViewNew.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderViewNew.setIndicatorUnselectedColor(Color.GRAY);
        //sliderViewNew.setScrollTimeInSec(3);
        sliderViewNew.setAutoCycle(false);
        //sliderViewNew.startAutoCycle();



        sliderView = view.findViewById(R.id.progressBarSlider);
        adapter = new SliderProgressBarAdapter(getContext(), this);
        sliderView.setSliderAdapter(adapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM
        // or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN
        // or SLIDE and SWAP!!
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(false);
        sliderView.startAutoCycle();
        /*sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });*/
        return view;
    }
}
