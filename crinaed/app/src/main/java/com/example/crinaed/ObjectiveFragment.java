package com.example.crinaed;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.crinaed.ProgressBar.SliderProgressBar;
import com.example.crinaed.ProgressBar.SliderProgressBarAdapter;
import com.example.crinaed.ProgressBar.SliderProgressBarModel;
import com.example.crinaed.ProgressBar.Step;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveFragment  extends Fragment {

     SliderProgressBar sliderView;
     SliderProgressBarAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_objective, container, false);

        sliderView = view.findViewById(R.id.progressBarSlider);

        adapter = new SliderProgressBarAdapter(getContext());
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
                renewItems(view);
            }
        });
        view.findViewById(R.id.addNewItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewItem(view);
            }
        });
        view.findViewById(R.id.removeLastItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeLastItem(view);
            }
        });
        return view;
    }

    public void renewItems(View view) {
        List<SliderProgressBarModel> sliderItemList = new ArrayList<>();

        //MENTAL
        SliderProgressBarModel mental = new SliderProgressBarModel();
        mental.setCategory(SliderProgressBarModel.MENTAL);
        mental.setDescription("Test desccrizione mentale");
        mental.setTitle("Test title");
        Step stepMental1 = new Step();
        stepMental1.setIsChecklist(false);
        stepMental1.setProgressPercentage(30.0);
        stepMental1.setDescription("Test stepMental1");
        Step stepMental2 = new Step();
        stepMental2.setIsChecklist(false);
        stepMental2.setProgressPercentage(50.0);
        stepMental2.setDescription("Test stepMental2");
        mental.getStepList().add(stepMental1);
        mental.getStepList().add(stepMental2);

        //PHYSICAL
        SliderProgressBarModel physical = new SliderProgressBarModel();
        physical.setCategory(SliderProgressBarModel.PHYSICAL);
        physical.setDescription("Test desccrizione mentale");
        physical.setTitle("Test title");
        Step stepPhysical1 = new Step();
        stepPhysical1.setIsChecklist(false);
        stepPhysical1.setProgressPercentage(30.0);
        stepPhysical1.setDescription("Test stepMental1");
        Step stepPhysical2 = new Step();
        stepPhysical2.setIsChecklist(false);
        stepPhysical2.setProgressPercentage(50.0);
        stepPhysical2.setDescription("Test stepMental2");
        physical.getStepList().add(stepPhysical1);
        physical.getStepList().add(stepPhysical2);

        //SOCIAL
        SliderProgressBarModel social = new SliderProgressBarModel();
        social.setCategory(SliderProgressBarModel.SOCIAL);
        social.setDescription("Test desccrizione mentale");
        social.setTitle("Test title");
        Step stepSocial1 = new Step();
        stepSocial1.setIsChecklist(false);
        stepSocial1.setProgressPercentage(30.0);
        stepSocial1.setDescription("Test stepMental1");
        Step stepSocial2 = new Step();
        stepSocial2.setIsChecklist(false);
        stepSocial2.setProgressPercentage(80.0);
        stepSocial2.setDescription("Test stepMental2");
        social.getStepList().add(stepSocial1);
        social.getStepList().add(stepSocial2);

        //add
        sliderItemList.add(mental);
        sliderItemList.add(social);
        sliderItemList.add(physical);
        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        //MENTAL
        SliderProgressBarModel mental = new SliderProgressBarModel();
        mental.setCategory(SliderProgressBarModel.MENTAL);
        mental.setDescription("Test desccrizione mentale");
        mental.setTitle("Test title");

        Step stepMental1 = new Step();
        stepMental1.setIsChecklist(false);
        stepMental1.setProgressPercentage(30.0);
        stepMental1.setDescription("Test stepMental1");
        Step stepMental2 = new Step();
        stepMental2.setIsChecklist(false);
        stepMental2.setProgressPercentage(70.0);
        stepMental2.setDescription("Test stepMental2");

        mental.getStepList().add(stepMental1);
        mental.getStepList().add(stepMental2);
        adapter.addItem(mental);
    }
}
