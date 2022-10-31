package com.example.crinaed.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import static androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL;


public class NestedScrollableHost extends FrameLayout {

    private int touchSlop;
    private float initialX = 0f;
    private float initialY = 0f;
    private ViewPager2 parentViewPager;
    private View child;

    public NestedScrollableHost(@NonNull Context context) {
        super(context);
    }

    public NestedScrollableHost(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        init();
    }

    private void init() {
        this.touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.initialX = 0f;
        this.initialY = 0f;
        View view = (View) getParent();
        while (view != null && !(view instanceof ViewPager2)){
            view = (View) view.getParent();
        }
        this.parentViewPager = (ViewPager2) view;
        this.child = getChildAt(0);
    }

    private boolean canChildScroll(int orientation,Float delta){
        int direction = - sign(delta);
        boolean result = false;
        switch (orientation){
            case 0:
                result = child != null ? child.canScrollHorizontally(direction): false;
                result = true;
                break;
            case 1:
                result = child != null ? child.canScrollVertically(direction): false;
                break;
        }
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        handleInterceptTouchEvent(e);
        return super.onInterceptTouchEvent(e);
    }

    private void handleInterceptTouchEvent(MotionEvent e){
        int orientation = this.parentViewPager.getOrientation();
        if(!canChildScroll(orientation,-1f) && !canChildScroll(orientation,1f)){
            return;
        }

        if(e.getAction() == MotionEvent.ACTION_DOWN) {
            this.initialX = e.getX();
            this.initialY = e.getY();
            getParent().requestDisallowInterceptTouchEvent(true);

        }else if(e.getAction() == MotionEvent.ACTION_MOVE) {
            float dx = e.getX() - initialX;
            float dy = e.getY() - initialY;
            boolean isVpHorizontal = orientation == ORIENTATION_HORIZONTAL;

            float scaledDx;
            float scaledDy;
            if (isVpHorizontal) {
                scaledDx = Math.abs(dx) * 5f;
                scaledDy = Math.abs(dy) * 1f;
            } else {
                scaledDx = Math.abs(dx) * 1f;
                scaledDy = Math.abs(dy) * 5f;
            }

            if (scaledDx > touchSlop || scaledDy > touchSlop) {
                if (isVpHorizontal == (scaledDy > scaledDx)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if (canChildScroll(orientation, isVpHorizontal ? dx: dy)){
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }else{
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
            }
        }
    }



    private int sign(float delta){
        if(delta > 0){
            return 1;
        }else if(delta < 0){
            return -1;
        }else{
            return 0;
        }
    }

}
