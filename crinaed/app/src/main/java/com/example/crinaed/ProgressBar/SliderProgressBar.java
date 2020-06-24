package com.example.crinaed.ProgressBar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.crinaed.R;
import com.smarteist.autoimageslider.SliderView;

public class SliderProgressBar extends SliderView {


    private SliderProgressBarAdapter sliderProgressBarAdapter;

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
        this.sliderProgressBarAdapter = (SliderProgressBarAdapter) super.getSliderAdapter();
        int primaryColor;
        int secondaryColor;
        switch(sliderProgressBarAdapter.getProgressBarModelList().get(position).getCategory()){
            case SliderProgressBarModel.SOCIAL:
                primaryColor = ContextCompat.getColor(getContext(), R.color.greenPrimary);
                secondaryColor = ContextCompat.getColor(getContext(),R.color.greenSecondary);
                break;
            case SliderProgressBarModel.MENTAL:
                primaryColor = ContextCompat.getColor(getContext(),R.color.bluPrimary);
                secondaryColor = ContextCompat.getColor(getContext(),R.color.bluSecondary);
                break;
            case SliderProgressBarModel.PHYSICAL:
                primaryColor = ContextCompat.getColor(getContext(),R.color.redPrimary);
                secondaryColor = ContextCompat.getColor(getContext(),R.color.redSecondary);
                break;
            default:
                primaryColor = ContextCompat.getColor(getContext(),R.color.redPrimary);
                secondaryColor = ContextCompat.getColor(getContext(),R.color.redSecondary);
                break;
        }
        super.setIndicatorSelectedColor(secondaryColor);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        Log.d("cri","funzioni ??");
        this.getParent().requestDisallowInterceptTouchEvent(false);
        super.onTouchEvent(ev);
        return true;
    }

}
