package com.example.crinaed.layout.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.crinaed.ProgressBar.GraphAdapter;
import com.example.crinaed.ProgressBar.SliderProgressBar;
import com.example.crinaed.ProgressBar.SliderProgressBarAdapter;
import com.example.crinaed.ProgressBar.SliderProgressBarAdapterNew;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.view.MySliderView;
import com.example.crinaed.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class ObjectiveFragment  extends Fragment {

     SliderProgressBar sliderView;
     SliderProgressBarAdapter adapter;

    MySliderView sliderViewNew;
    SliderProgressBarAdapterNew adapterNew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_objective, container, false);


        sliderViewNew = view.findViewById(R.id.progress_bar_slider);
        adapterNew = new SliderProgressBarAdapterNew(getContext(), this);
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


        SliderView currentSlider = view.findViewById(R.id.slider_currently_objective);
        GraphAdapter adapterCurrent = new GraphAdapter(this, getContext(), false, new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                return new Object[0];
            }
        });
        currentSlider.setSliderAdapter(adapterCurrent);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM
        // or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN
        // or SLIDE and SWAP!!
        currentSlider.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        currentSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        currentSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        currentSlider.setIndicatorUnselectedColor(Color.GRAY);
        //sliderViewNew.setScrollTimeInSec(3);
        currentSlider.setAutoCycle(false);
        //sliderViewNew.startAutoCycle();



        final SliderView oldSlider = view.findViewById(R.id.slider_old_objective);
        GraphAdapter oldCurrent = new GraphAdapter(this, getContext(), true, new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                if((Integer) paramether[0]==0){
                    oldSlider.setVisibility(View.GONE);
                } else{
                    oldSlider.setVisibility(View.VISIBLE);
                }
                return new Object[0];
            }
        });
        oldSlider.setSliderAdapter(oldCurrent);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM
        // or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN
        // or SLIDE and SWAP!!
        oldSlider.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        oldSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        oldSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        oldSlider.setIndicatorUnselectedColor(Color.GRAY);
        //sliderViewNew.setScrollTimeInSec(3);
        oldSlider.setAutoCycle(false);
        //sliderViewNew.startAutoCycle();

        return view;
    }
}
