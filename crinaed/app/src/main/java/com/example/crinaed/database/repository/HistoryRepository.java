package com.example.crinaed.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.HistoryDao;
import com.example.crinaed.database.entity.CourseBought;
import com.example.crinaed.database.entity.History;
import com.example.crinaed.database.entity.join.HistoryWithExercise;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryRepository implements Repository{
    private HistoryDao historyDao;
    public HistoryRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        historyDao = db.historyDao();
    }

    public LiveData<List<HistoryWithExercise>> getHistory(){
        return historyDao.getUserHistory();
    }

    public void insert(final History... histories){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                historyDao.insert(histories);
            }
        });
    }

    public void update(final History... histories){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                historyDao.update(histories);
            }
        });
    }

    public void delete(final History... histories){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                historyDao.delete(histories);
            }
        });
    }

    @Override
    public void loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("History");
        final List<History> histories = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            History history = new History();
            history.date = new Date(obj.getLong("date"));
            history.idExercise = obj.getLong("idExercise");
            history.idUser = obj.getLong("idUser");
            histories.add(history);
        }
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                historyDao.insert((History[]) histories.toArray());
            }
        });
    }
}
