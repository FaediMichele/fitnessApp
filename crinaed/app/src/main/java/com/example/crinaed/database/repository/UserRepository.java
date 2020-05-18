package com.example.crinaed.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.UserDao;
import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserLevel;
import com.example.crinaed.database.entity.UserSchoolCrossRef;
import com.example.crinaed.database.entity.join.user.UserCommitment;
import com.example.crinaed.database.entity.join.user.UserCourseBought;
import com.example.crinaed.database.entity.join.user.UserData;
import com.example.crinaed.database.entity.join.user.UserExercise;
import com.example.crinaed.database.entity.join.user.UserHistory;
import com.example.crinaed.database.entity.join.user.UserIscription;
import com.example.crinaed.database.entity.join.user.UserReview;
import com.example.crinaed.util.Category;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<UserData>> data;
    private LiveData<List<UserExercise>> exercises;
    private LiveData<List<UserIscription>> inscriptions;
    private LiveData<List<UserReview>> reviews;
    private LiveData<List<UserHistory>> history;

    public UserRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        data = userDao.getData();
        exercises = userDao.getExercise();
        inscriptions = userDao.getInscription();
        reviews = userDao.getReview();
        history = userDao.getHistory();
    }

    public LiveData<List<UserData>> getData() {
        return data;
    }

    public LiveData<List<UserExercise>> getExercise(){
        return exercises;
    }

    public LiveData<List<UserIscription>> getInscriptions(){
        return inscriptions;
    }

    public  LiveData<List<UserReview>> getReviews(){
        return reviews;
    }

    public LiveData<List<UserHistory>> getHistory() {
        return history;
    }

    public void addUser(final User user ){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insert(user);
            }
        });

        // Also add the level for each category.
        Category[] cats = Category.values();
        for(int i = 0; i < cats.length; i++){
            final UserLevel level = new UserLevel();
            level.cat = cats[i].toString();
            level.idUser = user.idUser;
            level.level = 1;
            level.PE = 0;
            AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    userDao.insert(level);
                }
            });
        }

    }

    public void addInscription(final UserSchoolCrossRef inscription){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insert(inscription);
            }
        });
    }

    public void updateUser(final User user){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.update(user);
            }
        });
    }

    public void updateUserLevel(final UserLevel level){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.update(level);
            }
        });
    }

    public void deleteInscription(final UserSchoolCrossRef inscription){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.delete(inscription);
            }
        });
    }
}
