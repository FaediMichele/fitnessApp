package com.example.crinaed.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.UserDao;
import com.example.crinaed.database.entity.History;
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
import java.util.Date;
import java.util.List;

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

    @Override
    public void loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("User");
        final List<User> users = new ArrayList<>();
        final List<UserLevel> userLevels = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            User user = new User();
            user.idUser = obj.getLong("idUser");
            user.firstname = obj.getString("firstname");
            user.surname = obj.getString("surname");
            user.email = obj.getString("email");
            user.hashPassword = obj.getString("hashPassword");
            users.add(user);
        }
        array = data.getJSONArray("Level");
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            UserLevel userLevel = new UserLevel();
            userLevel.idUser = obj.getLong("idUser");
            userLevel.cat = obj.getString("cat");
            userLevel.PE = obj.getInt("PE");
            userLevel.level = obj.getInt("level");
            userLevels.add(userLevel);
        }
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insert((User[]) users.toArray());
                userDao.insert((UserLevel[]) userLevels.toArray());
            }
        });
    }
}
