package com.example.crinaed.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.ExerciseInProgressDao;
import com.example.crinaed.database.entity.ExerciseInProgress;
import com.example.crinaed.database.entity.join.ExerciseInProgressWithExercise;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExerciseInProgressRepository implements Repository {
    private ExerciseInProgressDao exerciseInProgressDao;
    private LiveData<List<ExerciseInProgressWithExercise>>  exercise;

    public ExerciseInProgressRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        exerciseInProgressDao = db.exerciseInProgressDao();
        exercise = exerciseInProgressDao.get();
    }
    public LiveData<List<ExerciseInProgressWithExercise>> getExercise(){
        return exercise;
    }

    public void insert(final ExerciseInProgress... exercise){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseInProgressDao.insert(exercise);
            }
        });
    }

    public void update(final ExerciseInProgress... exercise){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseInProgressDao.update(exercise);
            }
        });
    }

    public void delete(final ExerciseInProgress... exercise){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseInProgressDao.delete(exercise);
            }
        });
    }

    @Override
    public void loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("CourseBought");
        final List<ExerciseInProgress> exercises = new ArrayList<>();
        for(int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            ExerciseInProgress exerciseInProgress = new ExerciseInProgress();
            exerciseInProgress.idUser = obj.getLong("idUser");
            exerciseInProgress.idExercise = obj.getLong("idExercise");
            exerciseInProgress.numStep = obj.getInt("numStep");
            exerciseInProgress.progression = obj.getDouble("progression");
            exerciseInProgress.lastEdit = new Date(obj.getLong("lastEdit"));
            exercises.add(exerciseInProgress);
        }
        insert((ExerciseInProgress[]) exercises.toArray());
    }
}
