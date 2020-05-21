package com.example.crinaed.database.repository;

import android.app.Application;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.UserDao;
import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserLevel;
import com.example.crinaed.database.entity.UserSchoolCrossRef;
import com.example.crinaed.database.entity.join.user.UserData;
import com.example.crinaed.database.entity.join.user.UserInscription;
import com.example.crinaed.util.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class UserRepository implements Repository{
    private UserDao userDao;
    private LiveData<List<UserData>> userData;
    private LiveData<List<UserInscription>> inscriptions;

    public UserRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        userData = userDao.getData();
        inscriptions = userDao.getInscription();
    }

    public LiveData<List<UserData>> getData() {
        return userData;
    }
    public LiveData<List<UserInscription>> getInscription(){
        return inscriptions;
    }

    public Future<?> addUser(final User user, final UserLevel... levels){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Pair<Long, Long[]>>() {
            @Override
            public Pair<Long, Long[]> call() {
                long idUser = userDao.insert(user)[0];
                return new Pair<>(idUser, userDao.insert(levels));
            }
        });
    }

    public Future<?> addInscription(final UserSchoolCrossRef inscription){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                userDao.insert(inscription);
            }
        });
    }

    public Future<?> updateUser(final User user){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                userDao.update(user);
            }
        });
    }

    public Future<?> updateUserLevel(final UserLevel level){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                userDao.update(level);
            }
        });
    }

    public Future<?> deleteInscription(final UserSchoolCrossRef inscription){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                userDao.delete(inscription);
            }
        });
    }

    @Override
    public Future<?> loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("User");
        final List<User> users = new ArrayList<>();
        final List<UserLevel> userLevels = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            User user = new User(obj.getLong("idUser"), obj.getString("firstname"), obj.getString("surname"),
                    obj.getString("email"), obj.getString("hashPassword"));
            users.add(user);
        }
        array = data.getJSONArray("Level");
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            UserLevel userLevel = new UserLevel(obj.getLong("idUser"), obj.getString("cat"), obj.getInt("PE"), obj.getInt("level"));
            userLevels.add(userLevel);
        }
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                userDao.insert(users.toArray(new User[0]));
                userDao.insert(userLevels.toArray(new UserLevel[0]));
            }
        });
    }
}
