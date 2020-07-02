package com.example.crinaed.database.repository;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.database.dao.ExerciseAndStepDao;
import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.Step;
import com.example.crinaed.database.entity.join.ExerciseWithStep;
import com.example.crinaed.util.Lambda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ExerciseAndStepRepository extends Repository{
    private ExerciseAndStepDao exerciseAndStepDao;
    private long lastExerciseId=-1;
    private Context context;

    public ExerciseAndStepRepository(Context context){
        this.context=context;
        AppDatabase db = AppDatabase.getDatabase(context);
        exerciseAndStepDao = db.exerciseAndStepDao();
    }

    public LiveData<List<Exercise>> getExercise(){
        return exerciseAndStepDao.getExercise();
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

    public Future<?> update(final Exercise... exercise){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                exerciseAndStepDao.update(exercise);
            }
        });
    }

    public Future<?> update(final Step... steps){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                exerciseAndStepDao.update(steps);
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
        final JSONArray array = data.getJSONArray("Exercise");
        final List<Exercise> exercises = new ArrayList<>();
        final List<Step> steps = new ArrayList<>();
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
        }
        final JSONArray array2 = data.getJSONArray("Step");
        for(int i = 0; i < array2.length(); i++){
            final Step s=new Step(array2.getJSONObject(i));
            steps.add(s);
            downloadVideo(array, i, new Lambda() {
                    @Override
                    public Object[] run(Object... parameter) {
                        if((Boolean) parameter[0]) {
                            File f = (File) parameter[1];
                            s.videoDownloaded = true;
                            s.video = f.getAbsolutePath();
                            update(s);
                            Log.d("video", "step "+f.getAbsolutePath() + " file saved |" + f.getTotalSpace());
                        }
                        return null;
                    }
                });
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
