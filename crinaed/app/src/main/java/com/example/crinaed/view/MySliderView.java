package com.example.crinaed.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.crinaed.util.Pair;
import com.smarteist.autoimageslider.SliderView;

public class MySliderView extends SliderView {

    final private static int SOCIAL  = 0;
    final private static int MENTAL  = 1;
    final private static int PHYSICAL= 2;

    public MySliderView(Context context) {
        super(context);
    }

    public MySliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySliderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Change color of indicator selected
     */
    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        MySliderAdapter sliderProgressBarNewAdapter = (MySliderAdapter) super.getSliderAdapter();
        Pair<Integer, Integer> color = sliderProgressBarNewAdapter.getColorForPage(position);
        super.setIndicatorSelectedColor(color.getX());
    }
}
