package com.example.crinaed.database;

import android.util.Log;

import com.example.crinaed.database.repository.Repository;
import com.example.crinaed.util.Lambda;

import org.json.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DatabaseUtil {
    private static DatabaseUtil instance;
    private List<Repository> repositories;

    public static DatabaseUtil getInstance(){
        if(instance == null){
            synchronized (DatabaseUtil.class){
                if(instance == null){
                    instance = new DatabaseUtil();
                    instance.repositories = new ArrayList<>();
                }
            }
        }
        return instance;
    }

    public void addRepository(final Repository repository){
        repositories.add(repository);
    }

    public void loadNewData(AppDatabase db, String data) throws JSONException, ExecutionException, InterruptedException {
        db.clearAllTables();
        JSONObject json = new JSONObject(data);
        for(int i = 0; i < repositories.size(); i++){
            repositories.get(i).loadData(json).get();
        }
    }

    /**
     * TODO
     * @param url url to download the video
     * @param l the callback function to call when the video is downloaded and stored
     */
    public void downloadVideo(String url, Lambda l){
        throw new UnsupportedOperationException();
    }
}
