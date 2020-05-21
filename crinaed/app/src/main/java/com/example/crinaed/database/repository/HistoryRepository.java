package com.example.crinaed.database.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.HistoryDao;
import com.example.crinaed.database.entity.History;
import com.example.crinaed.database.entity.join.HistoryWithExercise;
import com.example.crinaed.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class HistoryRepository implements Repository{
    private HistoryDao historyDao;
    public HistoryRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        historyDao = db.historyDao();
    }

    public LiveData<List<HistoryWithExercise>> getHistory(){
        return historyDao.getUserHistory();
    }

    public Future<?> insert(final History... histories){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Long[]>() {
            @Override
            public Long[] call() {
                return historyDao.insert(histories);
            }
        });
    }

    public Future<?> update(final History... histories){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                historyDao.update(histories);
            }
        });
    }

    public Future<?> delete(final History... histories){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                historyDao.delete(histories);
            }
        });
    }

    @Override
    public Future<?> loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("History");
        final List<History> histories = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            History history = new History(obj.getLong("idUser"), Util.isoFormatToTimestamp(obj.getString("date")), obj.getLong("idExercise"));
            histories.add(history);
        }
        return insert(histories.toArray(new History[0]));
    }
}
