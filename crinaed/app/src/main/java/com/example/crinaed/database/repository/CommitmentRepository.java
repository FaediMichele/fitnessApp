package com.example.crinaed.database.repository;

import android.app.Application;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.MyCommitmentDao;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class CommitmentRepository implements Repository{
    private MyCommitmentDao commitmentDao;

    public CommitmentRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        commitmentDao = db.commitmentDao();
    }

    public LiveData<List<CommitmentWithMyStep>> getCommitmentWithSteps() {
        return commitmentDao.getCommitments();
    }

    public Future<?> insert(final MyCommitment commitment, final MyStep... steps){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Pair<Long, Long[]>>() {
            @Override
            public Pair<Long, Long[]> call() {
                long id = commitmentDao.insert(commitment)[0];
                for (MyStep s: steps) {
                    s.idCommitment = id;
                }
                return new Pair<>(id, commitmentDao.insert(steps));
            }
        });
    }

    public Future<?> updateCommitment(final MyCommitment commitment){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                commitmentDao.update(commitment);
            }
        });
    }

    public Future<?> updateStep(final MyStep... steps){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                commitmentDao.update(steps);
            }
        });
    }

    public Future<?> deleteCommitment(final MyCommitment commitment){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                commitmentDao.delete((MyStep[]) commitmentDao.getStepByIdCommitment(commitment.idCommitment).toArray());
                commitmentDao.delete(commitment);
            }
        });
    }

    public Future<?> deleteStep(final MyStep... steps){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                commitmentDao.delete(steps);
            }
        });
    }

    @Override
    public Future<?> loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("MyCommitment");
        final List<MyCommitment> commitmentList = new ArrayList<>();
        final List<MyStep> stepList = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            MyCommitment myCommitment = new MyCommitment( obj.getLong("idCommitment"), obj.getString("name"),
                    obj.getString("desc"), obj.getInt("duration"),  obj.getLong("idUser"));
            commitmentList.add(myCommitment);
        }
        array = data.getJSONArray("MyStep");
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            MyStep myStep = new MyStep(obj.getLong("idCommitment"), obj.getInt("num"), obj.getString("name"),
                    obj.getDouble("incVal"),  obj.getString("unitMeasure"), obj.getDouble("max"), obj.getDouble("progression"));
            stepList.add(myStep);
        }

        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                commitmentDao.insert(commitmentList.toArray(new MyCommitment[0]));
                commitmentDao.insert(stepList.toArray(new MyStep[0]));
            }
        });
    }
}
