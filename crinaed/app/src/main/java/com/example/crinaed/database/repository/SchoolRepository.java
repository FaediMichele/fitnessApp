package com.example.crinaed.database.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.SchoolDao;
import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.join.SchoolData;
import com.example.crinaed.util.Lambda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class SchoolRepository implements Repository {
    private SchoolDao schoolDao;
    private LiveData<List<SchoolData>> data;

    public SchoolRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        schoolDao = db.schoolDao();
        data = schoolDao.get();
    }

    public LiveData<List<SchoolData>> getSchool(){
        return data;
    }

    public LiveData<List<SchoolData>> getSchoolTrained(int idTrainer){
        return schoolDao.getSchoolTrained(idTrainer);
    }


    public Future<?> insert(final Lambda l, final School... school){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Long[]>() {
            @Override
            public Long[] call() {
                return schoolDao.insert(school);
            }
        });
    }

    public Future<?> insert(final Course... courses){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Long[]>() {
            @Override
            public Long[] call() {
                return schoolDao.insert(courses);
            }
        });
    }

    public Future<?> update(final School... schools){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                schoolDao.update(schools);
            }
        });
    }

    public Future<?> update(final Course... courses){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                schoolDao.update(courses);
            }
        });
    }

    public Future<?> delete(final School... schools){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                schoolDao.delete(schools);
            }
        });
    }

    public Future<?> delete(final Course... courses){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                schoolDao.delete(courses);
            }
        });
    }

    @Override
    public Future<?> loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("School");
        final List<School> schools = new ArrayList<>();
        final List<Course> courses = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            School school = new School(obj.getLong("idSchool"), obj.getString("name"),
                    obj.getString("email"), obj.getString("address"),  obj.getLong("idTrainer"));
            schools.add(school);
        }
        array = data.getJSONArray("Course");
        for(int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Course course = new Course(obj.getLong("idCourse"), obj.getString("cat"),
                    obj.getString("name"), obj.getString("desc"), obj.getInt("minimumLevel"),  obj.getLong("idSchool"));
            courses.add(course);
        }
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                schoolDao.insert(schools.toArray(new School[0]));
                schoolDao.insert(courses.toArray(new Course[0]));
            }
        });
    }
}
