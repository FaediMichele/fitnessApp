package com.example.crinaed.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.CourseBoughtDao;
import com.example.crinaed.database.entity.CourseBought;
import com.example.crinaed.database.entity.join.user.UserCourseBought;

import java.util.List;

public class CourseBoughtRepository {

    private CourseBoughtDao courseBoughtDao;
    private LiveData<List<UserCourseBought>> courses;

    public CourseBoughtRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);

        courseBoughtDao = db.courseBoughtDao();
        courses = courseBoughtDao.getCourseBought();
    }

    public LiveData<List<UserCourseBought>> getCourses() {
        return courses;
    }

    public void addCourseBought(final CourseBought courseBought){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                courseBoughtDao.insert(courseBought);
            }
        });
    }

    public void updateCourseBought(final CourseBought courseBought){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                courseBoughtDao.update(courseBought);
            }
        });
    }

    public void deleteCourseBought(final CourseBought courseBought){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                courseBoughtDao.delete(courseBought);
            }
        });
    }
}
