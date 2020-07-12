package com.example.crinaed.ProgressBar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.crinaed.R;
import com.example.crinaed.util.Pair;
import com.example.crinaed.util.Period;
import com.smarteist.autoimageslider.SliderView;

public class SliderProgressBar extends SliderView {
    
    public SliderProgressBar(Context context) {
        super(context);
    }

    public SliderProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SliderProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Change color of indicator selected
     */
    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        SliderProgressBarAdapter sliderProgressBarAdapter = (SliderProgressBarAdapter) super.getSliderAdapter();
        Pair<Integer,Integer> color = sliderProgressBarAdapter.getCategoryForPosition(position).toColor(getContext());
        super.setIndicatorSelectedColor(color.getX());
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        this.getParent().requestDisallowInterceptTouchEvent(false);
        super.onTouchEvent(ev);
        return true;
    }

}
