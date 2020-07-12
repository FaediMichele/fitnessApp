package com.example.crinaed.ProgressBar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.crinaed.R;
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
        int primaryColor;
        int secondaryColor;
        //SliderProgressBarNewAdapter.getCategoryForPosition(position) //-------------------------------------------bisogna prendere la categoria dentro l'obbiettivo
        switch(position % 3){
            case SOCIAL:
                primaryColor = ContextCompat.getColor(getContext(), R.color.greenPrimary);
                secondaryColor = ContextCompat.getColor(getContext(),R.color.greenSecondary);
                break;
            case MENTAL:
                primaryColor = ContextCompat.getColor(getContext(),R.color.bluPrimary);
                secondaryColor = ContextCompat.getColor(getContext(),R.color.bluSecondary);
                break;
            case PHYSICAL:
                primaryColor = ContextCompat.getColor(getContext(),R.color.redPrimary);
                secondaryColor = ContextCompat.getColor(getContext(),R.color.redSecondary);
                break;
            default:
                primaryColor = ContextCompat.getColor(getContext(),R.color.redPrimary);
                secondaryColor = ContextCompat.getColor(getContext(),R.color.redSecondary);
                break;
        }
        super.setIndicatorSelectedColor(primaryColor);
    }
}
