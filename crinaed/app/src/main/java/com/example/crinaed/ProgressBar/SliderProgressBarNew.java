package com.example.crinaed.ProgressBar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.crinaed.R;
import com.example.crinaed.util.Pair;
import com.smarteist.autoimageslider.SliderView;

public class SliderProgressBarNew extends SliderView {

    final private static int SOCIAL  = 0;
    final private static int MENTAL  = 1;
    final private static int PHYSICAL= 2;

    private SliderProgressBarAdapterNew SliderProgressBarNewAdapter;

    public SliderProgressBarNew(Context context) {
        super(context);
    }

    public SliderProgressBarNew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SliderProgressBarNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Change color of indicator selected
     */
    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        this.SliderProgressBarNewAdapter = (SliderProgressBarAdapterNew) super.getSliderAdapter();
        Pair<Integer, Integer> color = this.SliderProgressBarNewAdapter.getColorForPage(position);
        super.setIndicatorSelectedColor(color.getX());
    }
}
