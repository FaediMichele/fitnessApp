package com.example.crinaed.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.ExerciseAndStepDao;
import com.example.crinaed.database.entity.CourseBought;
import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.Step;
import com.example.crinaed.database.entity.join.ExerciseWithStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExerciseAndStepRepository implements Repository{
    private ExerciseAndStepDao exerciseAndStepDao;

    public ExerciseAndStepRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        exerciseAndStepDao = db.exerciseAndStepDao();
    }

    public LiveData<List<ExerciseWithStep>> getCourseExercise(long idCourse){
        return exerciseAndStepDao.get(idCourse);
    }

    public void insert(final Exercise exercise, final Step... steps){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                long id = exerciseAndStepDao.insert(exercise)[0];
                for (Step s: steps) {
                    s.idExercise = id;
                }
                exerciseAndStepDao.insert(steps);
            }
        });
    }

    public void delete(final Exercise exercise){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseAndStepDao.delete((Step[]) exerciseAndStepDao.getStepByIdExercise(exercise.idExercise).toArray());
                exerciseAndStepDao.delete(exercise);
            }
        });
    }

    @Override
    public void loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("Exercise");
        final List<Exercise> exercises = new ArrayList<>();
        final List<Step> steps = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            Exercise exercise = new Exercise();
            exercise.idExercise = obj.getLong("idExercise");
            exercise.level = obj.getInt("level");
            exercise.PE = obj.getInt("PE");
            exercise.duration = obj.getInt("duration");
            exercise.name = obj.getString("name");
            exercise.desc = obj.getString("desc");
            /* TODO DatabaseUtil.getInstance().downloadVideo(obj.getString("video"), (urlSavedVideo) -> exercise.video = urlSavedVideo); */
            exercises.add(exercise);
        }
        array = data.getJSONArray("Step");
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            Step step = new Step();
            step.idExercise = obj.getLong("idExercise");
            step.num = obj.getInt("num");
            step.name = obj.getString("name");
            step.desc = obj.getString("desc");
            step.incVal = obj.getDouble("incVal");
            step.unitMeasure = obj.getString("unitMeasure");
            step.max = obj.getDouble("max");
            /* TODO DatabaseUtil.getInstance().downloadVideo(obj.getString("video"), (urlSavedVideo) -> step.video = urlSavedVideo); */
        }
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseAndStepDao.insert((Exercise[]) exercises.toArray());
                exerciseAndStepDao.insert((Step[]) steps.toArray());
            }
        });
    }
}
