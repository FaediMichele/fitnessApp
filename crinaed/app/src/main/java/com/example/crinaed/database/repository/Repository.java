package com.example.crinaed.database.repository;

import com.example.crinaed.database.entity.MyEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract class Repository {

    public abstract Future<?> loadData(final JSONObject data) throws JSONException;
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
}
