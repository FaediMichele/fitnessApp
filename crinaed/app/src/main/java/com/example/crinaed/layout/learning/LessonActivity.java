package com.example.crinaed.layout.learning;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.Exercise;


public class LessonActivity extends AppCompatActivity {
    public static final String ID_LESSON="ID_LESSON";

    private String nameFile = "video_lesson_simple.mp4";
    private VideoView mVideoView;
    private Integer msec = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//macna il back e le informazioni dell'utente
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        mVideoView = findViewById(R.id.video_lesson);
        final LessonFullScreenMediaController controller = new LessonFullScreenMediaController(this);
        final TextView desc = findViewById(R.id.text_description);
        final TextView title = findViewById(R.id.title_lesson);
        Bundle data = getIntent().getExtras();
        if(data != null && data.containsKey(ID_LESSON)){
            long idExercise = data.getLong(ID_LESSON);
            DatabaseUtil.getInstance().getRepositoryManager().getExerciseRepository().getExerciseById(idExercise).observe(this, new Observer<Exercise>() {
                @Override
                public void onChanged(Exercise exercise) {
                    if(exercise.videoDownloaded){
                        mVideoView.setVideoURI(Uri.parse(exercise.video));

                        controller.setVideoPath(exercise.video);
                    }
                    title.setText(exercise.name);
                    desc.setText(exercise.desc);
                }
            });
        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //int minute  = getIntent().getIntExtra(LessonFullScreenMediaController.KEY_MINUTE,0);

        /*String path = "android.resource://"+ getPackageName() + "/" + R.raw.video_simple ;  // if your video is not .mp4 change it your video extension
        Uri uri = Uri.parse(path);
        mVideoView.setVideoURI(uri);*/
//        mVideoView.start();


        mVideoView.setMediaController(controller);
        controller.setAnchorView(mVideoView);
        controller.setMediaPlayer(mVideoView);
//        mVideoView.seekTo(msec == null ? 0 : msec);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            controller.addOnUnhandledKeyEventListener(new View.OnUnhandledKeyEventListener() {
                @Override
                public boolean onUnhandledKeyEvent(View v, KeyEvent event) {
                    //Handle BACK button
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
                    {
                        controller.hide();
                    }
                    return true;
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LessonFullScreenVideoActivity.REQUEST_CODE_FULL_SCREEN_VIDEO_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                msec = data.getIntExtra(LessonFullScreenMediaController.KEY_MINUTE,0);
                mVideoView.seekTo(msec);
            }
        }
    }
}
