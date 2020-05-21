package com.example.crinaed.database.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.CourseBoughtDao;
import com.example.crinaed.database.entity.CourseBought;
import com.example.crinaed.database.entity.join.CourseBoughtWithCourse;
import com.example.crinaed.database.entity.join.user.UserCourseBought;
import com.example.crinaed.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class CourseBoughtRepository implements Repository{

    private CourseBoughtDao courseBoughtDao;

    public CourseBoughtRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        courseBoughtDao = db.courseBoughtDao();
    }

    public LiveData<List<CourseBoughtWithCourse>> getCourses() {
        return courseBoughtDao.getCourseBought();
    }

    public Future<?> insert(final CourseBought... courseBoughts){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Long[]>() {
            @Override
            public Long[] call() {
                return courseBoughtDao.insert(courseBoughts);
            }
        });
    }

    public Future<?> update(final CourseBought courseBought){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                courseBoughtDao.update(courseBought);
            }
        });
    }

    public Future<?> delete(final CourseBought courseBought){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                courseBoughtDao.delete(courseBought);
            }
        });
    }

    @Override
    public Future<?> loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("CourseBought");
        final List<CourseBought> courseBoughts = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            CourseBought courseBought = new CourseBought(obj.getLong("idUser"), obj.getLong("idCourse"),
                    obj.getInt("level"), Util.isoFormatToTimestamp(obj.getString("purchaseDate")));
            courseBoughts.add(courseBought);
        }
        return insert(courseBoughts.toArray(new CourseBought[0]));
    }
}
