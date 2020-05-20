package com.example.crinaed.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.CourseBoughtDao;
import com.example.crinaed.database.entity.CourseBought;
import com.example.crinaed.database.entity.join.user.UserCourseBought;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseBoughtRepository implements Repository{

    private CourseBoughtDao courseBoughtDao;

    public CourseBoughtRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        courseBoughtDao = db.courseBoughtDao();
    }

    public LiveData<List<UserCourseBought>> getCourses() {
        return courseBoughtDao.getCourseBought();
    }

    public void insert(final CourseBought... courseBoughts){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                courseBoughtDao.insert(courseBoughts);
            }
        });
    }

    public void update(final CourseBought courseBought){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                courseBoughtDao.update(courseBought);
            }
        });
    }

    public void delete(final CourseBought courseBought){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                courseBoughtDao.delete(courseBought);
            }
        });
    }

    @Override
    public void loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("CourseBought");
        final List<CourseBought> courseBoughts = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            CourseBought courseBought = new CourseBought();
            courseBought.idUser = obj.getLong("idUser");
            courseBought.idCourse = obj.getLong("idCourse");
            courseBought.level = obj.getInt("level");
            courseBought.purchaseDate = new Date(obj.getLong("purchaseDate"));
            courseBoughts.add(courseBought);
        }
        insert((CourseBought[]) courseBoughts.toArray());
    }
}
