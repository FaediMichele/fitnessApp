package com.example.crinaed.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.UserDao;
import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.join.UserData;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<UserData>> datas;

    public UserRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
    }

    public LiveData<List<UserData>> getDatas() {
        return datas;
    }

    public void addUser(final User user ){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insert(user);
            }
        });
    }
}
