package com.example.crinaed.database.repository;

import android.app.Application;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.MyCommitmentDao;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.MyStepDone;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.database.entity.join.MyStepWithMyStepDone;
import com.example.crinaed.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
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

    public LiveData<MyStepDoneWithMyStep> getStepOnGoing(long idMyStep){
        return commitmentDao.getLastMyStepDoneWithMyStep(idMyStep);
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

    public Future<List<MyStepDone>> updateMyStepDone(){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<List<MyStepDone>>() {
            @Override
            public List<MyStepDone> call() {
                List<CommitmentWithMyStep> l = commitmentDao.getCommitmentsList();
                List<MyStepDone> ret = new ArrayList<>();
                long now = new Date().getTime();
                for(int i = 0; i < l.size(); i++){
                    CommitmentWithMyStep c = l.get(i);
                    for(int j = 0; j < c.steps.size(); j++){
                        MyStep s = c.steps.get(i);
                        MyStepDone last = commitmentDao.getLastStepDone(s.idMyStep);
                        //Log.d("DatabaseTest update", "" + now + " , " + Util.timestampToIso(last.dateStart) + " , " + last.dateStart);
                        // now - last.dateStart is in millisecond
                        if(last == null || now - last.dateStart >  s.repetitionDay * 1000*60*60*24){
                            MyStepDone done = new MyStepDone(s.idMyStep, now, 0);
                            commitmentDao.insert(done);
                            ret.add(done);
                        }
                    }
                }
                return ret;
            }
        });
    }

    @Override
    public Future<?> loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("MyCommitment");
        final List<MyCommitment> commitmentList = new ArrayList<>();
        final List<MyStep> stepList = new ArrayList<>();
        final List<MyStepDone> stepDoneListList = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            MyCommitment myCommitment = new MyCommitment( obj.getLong("idCommitment"), obj.getString("name"),
                    obj.getString("desc"), Util.isoFormatToTimestamp(obj.getString("creationDate")),  obj.getLong("idUser"));
            commitmentList.add(myCommitment);
        }
        array = data.getJSONArray("MyStep");
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            MyStep myStep = new MyStep(obj.getLong("idMyStep"), obj.getLong("idCommitment"), obj.getString("name"),
                    obj.getString("unitMeasure"), obj.getDouble("max"), obj.getInt("repetitionDay"),
                    obj.getString("type"));
            stepList.add(myStep);
        }

        array = data.getJSONArray("MyStepDone");
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            MyStepDone myStepDone = new MyStepDone(obj.getLong("idMyStep"),
                    Util.isoFormatToTimestamp(obj.getString("dateStart")), obj.getInt("result"));
            stepDoneListList.add(myStepDone);
        }

        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                commitmentDao.insert(commitmentList.toArray(new MyCommitment[0]));
                commitmentDao.insert(stepList.toArray(new MyStep[0]));
                commitmentDao.insert(stepDoneListList.toArray(new MyStepDone[0]));
            }
        });
    }
}
