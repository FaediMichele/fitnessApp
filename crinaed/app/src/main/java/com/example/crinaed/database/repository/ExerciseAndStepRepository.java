package com.example.crinaed.database.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.ExerciseAndStepDao;
import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.Step;
import com.example.crinaed.database.entity.join.ExerciseWithStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ExerciseAndStepRepository implements Repository{
    private ExerciseAndStepDao exerciseAndStepDao;

    public ExerciseAndStepRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        exerciseAndStepDao = db.exerciseAndStepDao();
    }

    public LiveData<List<ExerciseWithStep>> getCourseExercise(long idCourse){
        return exerciseAndStepDao.get(idCourse);
    }

    public Future<?> insert(final Exercise exercise, final Step... steps){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Pair<Long, Long[]>>() {
            @Override
            public Pair<Long, Long[]> call() {
                Long id = exerciseAndStepDao.insert(exercise)[0];
                for (Step s: steps) {
                    s.idExercise = id;
                }
                return new Pair<>(id, exerciseAndStepDao.insert(steps));
            }
        });
    }

    public Future<?> delete(final Exercise exercise){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                exerciseAndStepDao.delete((Step[]) exerciseAndStepDao.getStepByIdExercise(exercise.idExercise).toArray());
                exerciseAndStepDao.delete(exercise);
            }
        });
    }

    @Override
    public Future<?> loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("Exercise");
        final List<Exercise> exercises = new ArrayList<>();
        final List<Step> steps = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            Exercise exercise = new Exercise(obj.getLong("idExercise"), obj.getInt("level"), obj.getInt("PE"),
                    obj.getInt("duration"), obj.getString("name"), obj.getString("desc"), obj.getLong("idCourse"));
            /* TODO DatabaseUtil.getInstance().downloadVideo(obj.getString("video"), (urlSavedVideo) -> exercise.video = urlSavedVideo); */
            exercises.add(exercise);
        }
        array = data.getJSONArray("Step");
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            Step step = new Step(obj.getLong("idExercise"), obj.getInt("num"), obj.getString("name"),
                    obj.getString("desc"), obj.getDouble("incVal"), obj.getString("unitMeasure"), obj.getDouble("max"));
            /* TODO DatabaseUtil.getInstance().downloadVideo(obj.getString("video"), (urlSavedVideo) -> step.video = urlSavedVideo); */
        }
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                exerciseAndStepDao.insert(exercises.toArray(new Exercise[0]));
                exerciseAndStepDao.insert(steps.toArray(new Step[0]));
            }
        });
    }
}
