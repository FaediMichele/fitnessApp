package com.example.crinaed;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

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

public class MainActivity extends AppCompatActivity {
    SliderProgressBar sliderView;
    private SliderProgressBarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderView = findViewById(R.id.progressBarSlider);

        adapter = new SliderProgressBarAdapter();
        sliderView.setSliderAdapter(adapter);// da guardare

        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM
        // or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN
        // or SLIDE and SWAP!!
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        //sliderView.setIndicatorSelectedColor(ContextCompat.getColor(this, R.color.redPrimary));
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

    }

    public void renewItems(View view) {
        List<SliderProgressBarModel> sliderItemList = new ArrayList<>();

        //MENTAL
        SliderProgressBarModel mental = new SliderProgressBarModel();
        mental.setCategory(SliderProgressBarModel.MENTAL);
        mental.setDescription("Test desccrizione mentale");
        mental.setTitle("Test title");
        Step stepMental1 = new Step();
        stepMental1.setChecklist(false);
        stepMental1.setProgressPercentage(30.0);
        stepMental1.setDescription("Test stepMental1");
        Step stepMental2 = new Step();
        stepMental2.setChecklist(false);
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
        stepPhysical1.setChecklist(false);
        stepPhysical1.setProgressPercentage(30.0);
        stepPhysical1.setDescription("Test stepMental1");
        Step stepPhysical2 = new Step();
        stepPhysical2.setChecklist(false);
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
        stepSocial1.setChecklist(false);
        stepSocial1.setProgressPercentage(30.0);
        stepSocial1.setDescription("Test stepMental1");
        Step stepSocial2 = new Step();
        stepSocial2.setChecklist(false);
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
        stepMental1.setChecklist(false);
        stepMental1.setProgressPercentage(30.0);
        stepMental1.setDescription("Test stepMental1");
        Step stepMental2 = new Step();
        stepMental2.setChecklist(false);
        stepMental2.setProgressPercentage(70.0);
        stepMental2.setDescription("Test stepMental2");

        mental.getStepList().add(stepMental1);
        mental.getStepList().add(stepMental2);
        adapter.addItem(mental);
    }
}
