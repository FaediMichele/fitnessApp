package com.example.crinaed.database;

import com.example.crinaed.database.repository.Repository;
import com.example.crinaed.util.Lambda;

import org.json.*;

import java.util.List;

public class DatabaseUtil {
    private static DatabaseUtil instance;
    private List<Repository> repositories;

    public DatabaseUtil getInstance(){
        if(instance == null){
            synchronized (DatabaseUtil.class){
                if(instance == null){
                    instance = new DatabaseUtil();
                }
            }
        }
        return instance;
    }

    public void addRepository(final Repository repository){
        repositories.add(repository);
    }

    public void loadNewData(AppDatabase db, String data) throws JSONException {
        db.clearAllTables();
        JSONObject json = new JSONObject(data);
        for(int i = 0; i < repositories.size(); i++){
            repositories.get(i).loadData(json);
        }
    }

    /**
     * TODO
     * @param url url to download the video
     * @param l the callback function to call when the video is downloaded and stored
     */
    public void downloadVideo(String url, Lambda l){

    }
}
