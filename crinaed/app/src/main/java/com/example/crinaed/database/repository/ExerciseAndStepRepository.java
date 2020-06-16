package com.example.crinaed.database.repository;

import android.content.Context;
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

public class ExerciseAndStepRepository extends Repository{
    private ExerciseAndStepDao exerciseAndStepDao;
    private long lastExerciseId=-1;

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
                exercise.idExercise = lastExerciseId--;
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
            /* TODO DatabaseUtil.getInstance().downloadVideo(obj.getLong("idExercise"), (urlSavedVideo) -> exercise.video = urlSavedVideo); */
            exercises.add(new Exercise(array.getJSONObject(i)));
        }
        array = data.getJSONArray("Step");
        for(int i = 0; i < array.length(); i++){
            steps.add(new Step(array.getJSONObject(i)));
            /* TODO DatabaseUtil.getInstance().downloadVideo(obj.getLong("idExercise"), (urlSavedVideo) -> step.video = urlSavedVideo); */
        }
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                exerciseAndStepDao.insert(exercises.toArray(new Exercise[0]));
                exerciseAndStepDao.insert(steps.toArray(new Step[0]));
            }
        });
    }

    @Override
    public Future<?> extractData(final JSONObject root) {
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    root.put("Exercise", listToJSONArray(exerciseAndStepDao.getExerciseList()));
                    root.put("Step", listToJSONArray(exerciseAndStepDao.getStepList()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
