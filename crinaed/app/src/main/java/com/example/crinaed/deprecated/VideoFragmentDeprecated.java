package com.example.crinaed.deprecated;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.repository.ExerciseRepository;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class VideoFragmentDeprecated extends Fragment {
    private VideoView video;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        /*video = view.findViewById(R.id.video_view);

        ExerciseRepository repo = DatabaseUtil.getInstance().getRepositoryManager().getExerciseRepository();
        final LiveData<List<Exercise>> exercise =repo.getExercise();

        exercise.observe(Objects.requireNonNull(getActivity()), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(final List<Exercise> exercises) {
                for(int i=0; i < exercises.size(); i++){
                    if(exercises.get(i).videoDownloaded){
                        File f = new File(exercises.get(i).video);
                        /*ServerManager.getInstance(getContext()).downloadFile("Exercise-912.mp4", Environment.DIRECTORY_DOWNLOADS, new Lambda() {
                            @Override
                            public Object[] run(Object... paramether) {
                                if((Boolean)paramether[0]){
                                    File f = (File) paramether[1];
                                    ServerManager.getInstance(getContext()).uploadFile(f, 90, ServerManager.FileType.Exercise, new Lambda() {
                                        @Override
                                        public Object[] run(Object... paramether) {
                                            Log.d("upload", "result: " + paramether[0].toString());
                                            return new Object[0];
                                        }
                                    });
                                } else{
                                    Log.d("upload", "download failed");
                                }
                                return null;
                            }
                        });
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
                                URLConnection.guessContentTypeFromName(fileUri.toString()));
                        video.setVideoPath(f.getAbsolutePath());
                        video.start();
                    }
                }
            }
        });*/


        return view;
    }
}