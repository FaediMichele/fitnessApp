package com.example.crinaed.layout.learning;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class LessonActivity extends AppCompatActivity {
    public static final String ID_LESSON="ID_LESSON";

    private String nameFile = "video_lesson_simple.mp4";
    private VideoView mVideoView;
    private Exercise pageExercise;
    private LiveData<List<MyCommitment>> commitmentWithName;


    private Observer<List<MyCommitment>> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//macna il back e le informazioni dell'utente
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        mVideoView = findViewById(R.id.video_lesson);
        final LessonFullScreenMediaController controller = new LessonFullScreenMediaController(this);
        final TextView desc = findViewById(R.id.text_description);
        final TextView title = findViewById(R.id.title_lesson);
        final Button addToCommitment = findViewById(R.id.add_to_my_commitment);
        final AppCompatActivity activity = this;
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
                    pageExercise=exercise;
                    title.setText(exercise.name);
                    desc.setText(exercise.desc);
                    if(exercise.importData.length()<=2){
                        addToCommitment.setText(R.string.no_import_data);
                        addToCommitment.setEnabled(false);
                    }
                }
            });

            addToCommitment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        final JSONObject newData = new JSONObject(pageExercise.importData);
                        String name = newData.getJSONObject("MyCommitment").getString("name");
                        if(commitmentWithName!=null){
                            commitmentWithName.removeObserver(observer);
                        }
                        commitmentWithName = DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().getCommitmentWithName(name);
                        observer = new Observer<List<MyCommitment>>() {
                            @Override
                            public void onChanged(List<MyCommitment> list) {
                                Log.d("naed", Arrays.toString(list.toArray()));
                                if(list.size()>0){
                                    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
                                    adb.setTitle(getString(R.string.warning));
                                    adb.setMessage(getString(R.string.commitment_exist));
                                    adb.setIcon(android.R.drawable.ic_dialog_alert);
                                    adb.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            createNewCommitment(newData);
                                        }
                                    });
                                    adb.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    adb.show();
                                } else{
                                    createNewCommitment(newData);
                                }
                            }
                        };
                        commitmentWithName.observe(activity, observer);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    private void createNewCommitment(JSONObject obj){
        try {
            MyCommitment commitment = new MyCommitment(obj.getJSONObject("MyCommitment"));
            commitment.idUser= Util.getInstance().getIdUser();
            commitment.creationDate=new Date().getTime();
            JSONArray objSteps = obj.getJSONArray("MyStep");
            MyStep[] steps = new MyStep[objSteps.length()];
            for(int i=0; i<steps.length;i++){
                steps[i] = new MyStep(objSteps.getJSONObject(i));
            }
            this.commitmentWithName.removeObserver(observer);
            DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().insert(commitment, steps, new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    Snackbar.make(mVideoView.getRootView(), R.string.objective_added, BaseTransientBottomBar.LENGTH_LONG).show();
                    return new Object[0];
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LessonFullScreenVideoActivity.REQUEST_CODE_FULL_SCREEN_VIDEO_ACTIVITY){
            if(resultCode == Activity.RESULT_OK && data!= null){
                int msec = data.getIntExtra(LessonFullScreenMediaController.KEY_MINUTE, 0);
                mVideoView.seekTo(msec);
            }
        }
    }
}
