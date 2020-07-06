package com.example.crinaed.database.repository;

import android.content.Context;
import android.os.Environment;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.database.dao.UserDao;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserLevel;
import com.example.crinaed.database.entity.UserSchoolCrossRef;
import com.example.crinaed.database.entity.join.user.UserData;
import com.example.crinaed.database.entity.join.user.UserInscription;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class UserRepository extends Repository{
    private UserDao userDao;
    private Context context;

    private long lastId = -1;

    public UserRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        userDao=db.userDao();
        this.context=context;
    }

    public LiveData<List<UserData>> getData() {
        return userDao.getData(Util.getInstance().getIdUser());
    }
    public LiveData<List<UserInscription>> getInscription(){
        return  userDao.getInscription();
    }

    public Future<?> addUser(final User user, final UserLevel... levels){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Pair<Long, Long[]>>() {
            @Override
            public Pair<Long, Long[]> call() {
                user.idUser = lastId--;
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
        final List<UserSchoolCrossRef> inscription = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            final User u = new User( array.getJSONObject(i));
            users.add(new User( array.getJSONObject(i)));
            downloadImage(array,i,  new Lambda() {
                    @Override
                    public Object[] run(Object... paramether) {
                        if((Boolean) paramether[0]){
                            File f = (File) paramether[1];
                            u.image=f.getAbsolutePath();
                            u.imageDownloaded=true;
                            updateUser(u);
                        }
                        return new Object[0];
                    }
                });
        }
        array = data.getJSONArray("Level");
        for(int i = 0; i < array.length(); i++){
            userLevels.add(new UserLevel(array.getJSONObject(i)));
        }

        array = data.getJSONArray("School");
        for(int i = 0; i < array.length(); i++){
            inscription.add(new UserSchoolCrossRef(userLevels.get(0).idUser, array.getJSONObject(i).getLong("idSchool")));
        }
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                userDao.insert(users.toArray(new User[0]));
                userDao.insert(userLevels.toArray(new UserLevel[0]));
                userDao.insert(inscription.toArray(new UserSchoolCrossRef[0]));
            }
        });
    }


    @Override
    public Future<?> extractData(final JSONObject root) {
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    root.put("User", listToJSONArray(userDao.getUsersList()));
                    root.put("Level", listToJSONArray(userDao.getLevelList()));
                    root.put("Inscription", listToJSONArray(userDao.getInscriptionList()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
