package com.example.crinaed.view;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crinaed.R;

import static java.security.AccessController.getContext;

public class FullScreenVideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_video_view);
        videoView = findViewById(R.id.videoView);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Uri videoUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video_simple);
        videoView.setVideoURI(videoUri);
        mediaController = new FullScreenMediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        int minute  = this.getIntent().getIntExtra(FullScreenMediaController.KEY_MINUTE,0);
        videoView.seekTo(minute);
        //        videoView.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mediaController.addOnUnhandledKeyEventListener(new View.OnUnhandledKeyEventListener() {
                @Override
                public boolean onUnhandledKeyEvent(View v, KeyEvent event) {
                    //Handle BACK button
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
                    {
                        mediaController.hide(); //Hide mediaController,according to your needs, you can also called here onBackPressed() or finish()
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("cri","" + videoView.getCurrentPosition());
        super.onBackPressed();
    }
}