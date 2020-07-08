package com.example.crinaed.layout.learning;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crinaed.R;
import com.example.crinaed.view.FullScreenMediaController;
import com.example.crinaed.view.FullScreenVideoActivity;

public class LessonActivity extends AppCompatActivity {

    private String nameFile = "video_lesson_simple.mp4";
    private VideoView mVideoView;
    private Integer msec = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {//macna il back e le informazioni dell'utente
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //int minute  = getIntent().getIntExtra(FullScreenMediaController.KEY_MINUTE,0);
        mVideoView = findViewById(R.id.video_lesson);
        String path = "android.resource://"+ getPackageName() + "/" + R.raw.video_simple ;  // if your video is not .mp4 change it your video extension
        Uri uri = Uri.parse(path);
        mVideoView.setVideoURI(uri);
//        mVideoView.start();

        FullScreenMediaController controller = new FullScreenMediaController(this);
        mVideoView.setMediaController(controller);
        controller.setAnchorView(mVideoView);
        controller.setMediaPlayer(mVideoView);
//        mVideoView.seekTo(msec == null ? 0 : msec);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FullScreenVideoActivity.REQUEST_CODE_FULL_SCREEN_VIDEO_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                msec = data.getIntExtra(FullScreenMediaController.KEY_MINUTE,0);
                mVideoView.seekTo(msec);
            }
        }
    }
}
