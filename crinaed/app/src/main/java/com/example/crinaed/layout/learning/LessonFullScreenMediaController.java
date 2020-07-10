package com.example.crinaed.layout.learning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.core.content.ContextCompat;

import com.example.crinaed.R;

public class LessonFullScreenMediaController extends MediaController {

    final public static String KEY_MINUTE = "KEY_MINUTE";
    final private static String KEY_IS_FULL_SCREEN = "KEY_IS_FULL_SCREEN";
    private ImageButton fullScreen;
    private boolean isFullScreen;
    private String videoPath;

    public LessonFullScreenMediaController(Context context) {
        super(context);
    }

    @Override
    public void setAnchorView(final View view) {
        super.setAnchorView(view);

        //image button for full screen to be added to media controller
        fullScreen = new ImageButton (super.getContext());
        fullScreen.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.rightMargin = 10;
        params.topMargin = 12;
        addView(fullScreen, params);

        //fullscreen indicator from intent
        isFullScreen =  ((Activity)getContext()).getIntent().getBooleanExtra(KEY_IS_FULL_SCREEN,false);
        if(isFullScreen){
            fullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_exit);
        }else{
            fullScreen.setImageResource(R.drawable.ic_baseline_fullscreen);
        }

        //add listener to image button to handle full screen and exit full screen events
        fullScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(view);
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            this.hide();
        }
        return super.dispatchKeyEvent(event);
    }


    public void onBackPressed(View view) {
        VideoView videoView =  view.findViewById(R.id.video_lesson);
        if(isFullScreen){
            Intent returnIntent = new Intent();
            returnIntent.putExtra(KEY_MINUTE,videoView.getCurrentPosition());
            returnIntent.putExtra(KEY_IS_FULL_SCREEN, false);
            ((Activity)getContext()).setResult(Activity.RESULT_OK,returnIntent);
            ((Activity)getContext()).finish();
        }else{
            Intent intent = new Intent(getContext(),LessonFullScreenVideoActivity.class);
            intent.putExtra(LessonFullScreenVideoActivity.EXERCISE_VIDEO_PATH, videoPath);
            intent.putExtra(KEY_IS_FULL_SCREEN, true);
            intent.putExtra(KEY_MINUTE,videoView.getCurrentPosition());
            ((Activity)getContext()).startActivityForResult(intent,LessonFullScreenVideoActivity.REQUEST_CODE_FULL_SCREEN_VIDEO_ACTIVITY);
        }
    }

    public void setVideoPath(String video) {
        videoPath=video;
    }
}