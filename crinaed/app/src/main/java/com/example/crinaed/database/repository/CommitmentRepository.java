package com.example.crinaed.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.MyCommitmentDao;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.database.entity.join.user.UserCommitment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommitmentRepository implements Repository{
    private MyCommitmentDao commitmentDao;

    public CommitmentRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        commitmentDao = db.commitmentDao();
    }

    public LiveData<List<CommitmentWithMyStep>> getCommitmentWithSteps() {
        return commitmentDao.getCommitments();
    }

    public void insert(final MyCommitment commitment, final MyStep... steps){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                long id = commitmentDao.insert(commitment)[0];
                for (MyStep s: steps) {
                    s.idCommitment = id;
                }
                commitmentDao.insert(steps);
            }
        });

    }

    public void updateCommitment(final MyCommitment commitment){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                commitmentDao.update(commitment);
            }
        });
    }

    public void updateStep(final MyStep... steps){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                commitmentDao.update(steps);
            }
        });
    }

    public void deleteCommitment(final MyCommitment commitment){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                commitmentDao.delete((MyStep[]) commitmentDao.getStepByIdCommitment(commitment.idCommitment).toArray());
                commitmentDao.delete(commitment);
            }
        });
    }

    public void deleteStep(final MyStep... steps){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                commitmentDao.delete(steps);
            }
        });
    }

    @Override
    public void loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("MyCommitment");
        final List<MyCommitment> commitmentList = new ArrayList<>();
        final List<MyStep> stepList = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            MyCommitment myCommitment = new MyCommitment();
            myCommitment.idUser = obj.getLong("idUser");
            myCommitment.desc = obj.getString("desc");
            myCommitment.duration = obj.getInt("duration");
            myCommitment.idCommitment = obj.getLong("idCommitment");
            myCommitment.name = obj.getString("name");
            commitmentList.add(myCommitment);
        }
        array = data.getJSONArray("MyStep");
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            MyStep myStep = new MyStep();
            myStep.idCommitment = obj.getLong("idCommitment");
            myStep.num = obj.getInt("num");
            myStep.name = obj.getString("name");
            myStep.incVal = obj.getDouble("incVal");
            myStep.unitMeasure = obj.getString("unitMeasure");
            myStep.max = obj.getDouble("max");
            myStep.progression = obj.getDouble("progression");
            stepList.add(myStep);
        }
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                commitmentDao.insert((MyCommitment[]) commitmentList.toArray());
                commitmentDao.insert((MyStep[]) stepList.toArray());
            }
        });
    }
}
