package com.example.crinaed.layout.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.crinaed.ProgressBar.SliderProgressBar;
import com.example.crinaed.ProgressBar.SliderProgressBarAdapter;
import com.example.crinaed.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class ObjectiveFragment  extends Fragment {

     SliderProgressBar sliderView;
     SliderProgressBarAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_objective, container, false);

        sliderView = view.findViewById(R.id.progressBarSlider);

        adapter = new SliderProgressBarAdapter(getContext(), this);
        sliderView.setSliderAdapter(adapter);// da guardare

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
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });

        view.findViewById(R.id.renewItems).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //renewItems(view);
            }
        });
        view.findViewById(R.id.addNewItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addNewItem(view);
            }
        });
        view.findViewById(R.id.removeLastItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //removeLastItem(view);
            }
        });
        return view;
    }
}
