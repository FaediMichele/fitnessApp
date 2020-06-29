package com.example.crinaed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.repository.CommitmentRepository;
import com.example.crinaed.database.repository.ExerciseAndStepRepository;
import com.example.crinaed.util.Period;
import com.github.mikephil.charting.charts.LineChart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLConnection;
import java.util.List;
import java.util.Objects;

public class VideoFragment extends Fragment {
    private VideoView video;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        video = view.findViewById(R.id.video_view);

        ExerciseAndStepRepository repo = DatabaseUtil.getInstance().getRepositoryManager().getExerciseAndStepRepository();
        final LiveData<List<Exercise>> exercise =repo.getExercise();

        exercise.observe(Objects.requireNonNull(getActivity()), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                for(int i=0; i < exercises.size(); i++){
                    if(exercises.get(i).videoDownloaded){
                        File f = new File(exercises.get(i).video);
                        /*try(BufferedReader reader = new BufferedReader(new FileReader(f))){
                            String s=reader.readLine();
                            while(s!=null){
                                Log.d("file", s);
                                s=reader.readLine();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Uri fileUri = Uri.fromFile(f);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(fileUri,
                                URLConnection.guessContentTypeFromName(fileUri.toString()));*/
                        video.setVideoPath(f.getAbsolutePath());
                        video.start();
                    }
                }
            }
        });


        return view;
    }
}