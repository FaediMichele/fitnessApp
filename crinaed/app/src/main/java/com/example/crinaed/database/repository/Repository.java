package com.example.crinaed.database.repository;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.crinaed.database.ServerManager;
import com.example.crinaed.database.entity.MyEntity;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Single;
import com.example.crinaed.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
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
    public abstract Future<?> deleteAll();

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
        Util.downloadImage(array,i,context,l);
    }

    protected void downloadImageArray(final JSONArray array, final int i, final Lambda l)  {
        try {
            final JSONArray images=array.getJSONObject(i).getJSONArray("image");
            final String[] files = new String[images.length()];
            final Single<Integer> setted = new Single<>(0);
            for(int j=0;j<images.length();j++){
                final String file = images.getString(j);

                final int k=j;
                if(!file.equals("")){
                    ServerManager.getInstance(context).downloadFile(file, Environment.DIRECTORY_PICTURES, new Lambda() {
                        @Override
                        public Object[] run(Object... paramether) {
                            try{
                                if((Boolean)paramether[0]){
                                    files[k]=((File) paramether[1]).getAbsolutePath();
                                    Log.d("nade", "downloaded image: " + files[k]);
                                    synchronized (setted) {
                                        setted.setVal(setted.getVal() + 1);
                                    }
                                    if(setted.getVal()==images.length()){
                                        l.run(true, files);
                                    }
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                            return new Object[0];
                        }
                    });
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
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
