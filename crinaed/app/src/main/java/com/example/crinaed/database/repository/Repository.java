package com.example.crinaed.database.repository;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.crinaed.database.ServerManager;
import com.example.crinaed.database.entity.MyEntity;
import com.example.crinaed.util.Lambda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract class Repository {
    private Context context;
    protected void setContext(Context context){
        this.context=context;
    }
    protected Context getContext(){
        return context;
    }

    public abstract Future<?> loadData(final JSONObject data) throws Exception;
    public abstract Future<?> extractData(final JSONObject root);

    public <T extends MyEntity> JSONArray  listToJSONArray(final List<T> list){
        JSONArray ret = new JSONArray();
        for(T e : list){
            try {
                ret.put(e.toJson());
            } catch (JSONException ignored) {
            }
        }
        return ret;
    }

    protected void downloadImage(final JSONArray array, final int i, final Lambda l)  {
        try {
            final String file=array.getJSONObject(i).getString("image");
            if(!file.equals("")){
                ServerManager.getInstance(context).downloadFile(file, Environment.DIRECTORY_PICTURES, l);
                Log.d("download", "downloaded image: " + file);
            }
        } catch (JSONException ignore) {
        }
    }

    protected void downloadVideo(final JSONArray array, final int i, final Lambda l)  {
        try {
            final String file=array.getJSONObject(i).getString("video");
            if(!file.equals("")){
                ServerManager.getInstance(context).downloadFile(file, Environment.DIRECTORY_MOVIES, l);
                Log.d("download", "downloaded video: " + file);
            }
        } catch (JSONException ignore) {
        }
    }
}
