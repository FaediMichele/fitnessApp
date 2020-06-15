package com.example.crinaed.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.ExerciseInProgressDao;
import com.example.crinaed.database.entity.ExerciseInProgress;
import com.example.crinaed.database.entity.join.ExerciseInProgressWithExercise;
import com.example.crinaed.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ExerciseInProgressRepository extends Repository {
    private ExerciseInProgressDao exerciseInProgressDao;
    private LiveData<List<ExerciseInProgressWithExercise>>  exercise;

    public ExerciseInProgressRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        exerciseInProgressDao = db.exerciseInProgressDao();
        exercise = exerciseInProgressDao.get();
    }
    public LiveData<List<ExerciseInProgressWithExercise>> getExercise(){
        return exercise;
    }

    public Future<?> insert(final ExerciseInProgress... exercise){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Long[]>() {
            @Override
            public Long[] call() {
                return exerciseInProgressDao.insert(exercise);
            }
        });
    }

    public Future<?> update(final ExerciseInProgress... exercise){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                exerciseInProgressDao.update(exercise);
            }
        });
    }

    public Future<?> delete(final ExerciseInProgress... exercise){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                exerciseInProgressDao.delete(exercise);
            }
        });
    }

    @Override
    public Future<?> loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("ExerciseInProgress");
        final List<ExerciseInProgress> exercises = new ArrayList<>();
        for(int i = 0; i < array.length(); i++) {
            exercises.add(new ExerciseInProgress(array.getJSONObject(i)));
        }

        return insert(exercises.toArray(new ExerciseInProgress[0]));

    }

    @Override
    public Future<?> extractData(final JSONObject root){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    root.put("ExerciseInProgress", listToJSONArray(exerciseInProgressDao.getExerciseList()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
