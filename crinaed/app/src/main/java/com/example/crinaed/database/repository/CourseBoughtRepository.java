package com.example.crinaed.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.CourseBoughtDao;
import com.example.crinaed.database.entity.CourseBought;
import com.example.crinaed.database.entity.join.CourseBoughtWithCourse;
import com.example.crinaed.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class CourseBoughtRepository extends Repository{

    private CourseBoughtDao courseBoughtDao;

    public CourseBoughtRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
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
    public Future<?> loadData(final JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("CourseBought");
        final List<CourseBought> courseBoughts = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            courseBoughts.add(new CourseBought(array.getJSONObject(i)));
        }
        return insert(courseBoughts.toArray(new CourseBought[0]));
    }

    @Override
    public Future<?> extractData(final JSONObject root) {
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    root.put("CourseBought", listToJSONArray(courseBoughtDao.getCouseBoughtList()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
