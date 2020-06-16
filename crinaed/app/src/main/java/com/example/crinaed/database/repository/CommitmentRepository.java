package com.example.crinaed.database.repository;

import android.content.Context;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.MyCommitmentDao;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.MyStepDone;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class CommitmentRepository extends Repository{
    private MyCommitmentDao commitmentDao;

    // used for the insert. (the data created by the app must have negative ids)
    private long lastCommitmentId = -1;
    private long lastStepId = -1;

    public CommitmentRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        commitmentDao = db.commitmentDao();
    }

    public LiveData<List<CommitmentWithMyStep>> getCommitmentWithSteps() {
        return commitmentDao.getCommitmentWithMyStep();
    }

    public LiveData<MyStepDoneWithMyStep> getStepOnGoing(long idMyStep){
        return commitmentDao.getLastMyStepDoneWithMyStep(idMyStep);
    }

    public Future<Pair<Long, Long[]>> insert(final Lambda l, final MyCommitment commitment, final MyStep... steps){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Pair<Long, Long[]>>() {
                        @Override
                        public Pair<Long, Long[]> call() {
                commitment.idCommitment = lastCommitmentId--;
                long id = commitmentDao.insert(commitment)[0];
                for(MyStep s : steps){
                    s.idMyStep = lastStepId--;
                    s.idCommitment = commitment.idCommitment;
                }
                final Pair<Long, Long[]> ret = new Pair<>(id, commitmentDao.insert(steps));
                final long now = new Date().getTime();
                final MyStepDone[] myStepsDone = new MyStepDone[ret.second.length];
                for(int i = 0; i < ret.second.length; i++){
                    myStepsDone[i] = new MyStepDone(ret.second[i], now, 0);
                }
                commitmentDao.insert(myStepsDone);
                return ret;
            }
        });
    }

    // Not implemented in the server
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
                List<CommitmentWithMyStep> l = commitmentDao.getCommitmentWithMyStepList();
                List<MyStepDone> ret = new ArrayList<>();
                long now = new Date().getTime();
                for(int i = 0; i < l.size(); i++){
                    CommitmentWithMyStep c = l.get(i);
                    for(int j = 0; j < c.steps.size(); j++){
                        MyStep s = c.steps.get(i);
                        MyStepDone last = commitmentDao.getLastStepDone(s.idMyStep);
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
        final List<MyCommitment> commitmentList = new ArrayList<>();
        final List<MyStep> stepList = new ArrayList<>();
        final List<MyStepDone> stepDoneListList = new ArrayList<>();

        JSONArray array = data.getJSONArray("MyCommitment");
        for(int i = 0; i < array.length(); i++){
            commitmentList.add(new MyCommitment(array.getJSONObject(i)));
        }
        array = data.getJSONArray("MyStep");
        for(int i = 0; i < array.length(); i++){
            stepList.add(new MyStep(array.getJSONObject(i)));
        }

        array = data.getJSONArray("MyStepDone");
        for(int i = 0; i < array.length(); i++){
            stepDoneListList.add(new MyStepDone(array.getJSONObject(i)));
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

    @Override
    public Future<?> extractData(final JSONObject root) {
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    root.put("MyCommitment", listToJSONArray(commitmentDao.getCommitmentList()));
                    root.put("MyStep", listToJSONArray(commitmentDao.getMyStepList()));
                    root.put("MyStepDone", listToJSONArray(commitmentDao.getMyStepDoneList()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
