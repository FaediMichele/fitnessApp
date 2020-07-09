package com.example.crinaed.database.repository;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.ExerciseAndStepDao;
import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.util.Lambda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ExerciseRepository extends Repository{
    private ExerciseAndStepDao exerciseAndStepDao;

    public ExerciseRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        exerciseAndStepDao = db.exerciseAndStepDao();
    }

    public LiveData<List<Exercise>> getExerciseFromCourse(long idCourse){
        return exerciseAndStepDao.getExercise(idCourse);
    }

    public Future<?> update(final Exercise... exercise){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                exerciseAndStepDao.update(exercise);
            }
        });
    }

    public Future<?> delete(final Exercise exercise){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                exerciseAndStepDao.delete(exercise);
            }
        });
    }

    @Override
    public Future<?> loadData(JSONObject data) throws JSONException {
        final JSONArray array = data.getJSONArray("Exercise");
        final List<Exercise> exercises = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            final Exercise e=new Exercise(array.getJSONObject(i));
            exercises.add(e);
            downloadVideo(array, i, new Lambda() {
                    @Override
                    public Object[] run(Object... parameter) {
                        if((Boolean) parameter[0]) {
                            File f = (File) parameter[1];
                            e.videoDownloaded = true;
                            e.video = f.getAbsolutePath();
                            update(e);
                        }
                        return null;
                    }
                });
            downloadImage(array, i, new Lambda() {
                @Override
                public Object[] run(Object... parameter) {
                    if((Boolean) parameter[0]) {
                        File f = (File) parameter[1];
                        e.imageDownloaded = true;
                        e.image = f.getAbsolutePath();
                        update(e);
                    } else{
                        Log.d("naed", "image not downloaded  Exercise image : " + e.image);
                    }
                    return new Object[0];
                }
            });
        }
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                exerciseAndStepDao.insert(exercises.toArray(new Exercise[0]));
            }
        });
    }

    @Override
    public Future<?> extractData(final JSONObject root) {
        return null;
    }
}
