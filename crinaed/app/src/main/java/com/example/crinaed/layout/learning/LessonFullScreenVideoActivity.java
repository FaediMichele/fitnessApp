package com.example.crinaed.layout.learning;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crinaed.R;

public class LessonFullScreenVideoActivity extends AppCompatActivity {
    public static final String EXERCISE_VIDEO_PATH = "EXERCISE_VIDEO_PATH";
    final public static int REQUEST_CODE_FULL_SCREEN_VIDEO_ACTIVITY = 1;

    private VideoView videoView;
    private LessonFullScreenMediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_video_view);
        videoView = findViewById(R.id.video_lesson);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle data = getIntent().getExtras();
        if(data!= null && data.containsKey(EXERCISE_VIDEO_PATH)){
            videoView.setVideoURI(Uri.parse(data.getString(EXERCISE_VIDEO_PATH)));
        }
        mediaController = new LessonFullScreenMediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        int minute  = this.getIntent().getIntExtra(LessonFullScreenMediaController.KEY_MINUTE,0);
        videoView.seekTo(minute);
        //        videoView.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mediaController.addOnUnhandledKeyEventListener(new View.OnUnhandledKeyEventListener() {
                @Override
                public boolean onUnhandledKeyEvent(View v, KeyEvent event) {
                    //Handle BACK button
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
                    {
                        mediaController.hide();
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        mediaController.onBackPressed(videoView);
    }
}