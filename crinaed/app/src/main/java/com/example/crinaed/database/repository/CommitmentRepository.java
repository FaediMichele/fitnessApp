package com.example.crinaed.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.MyCommitmentDao;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.database.entity.join.user.UserCommitment;

import java.util.List;

public class CommitmentRepository {
    private MyCommitmentDao commitmentDao;
    private LiveData<List<UserCommitment>> commitment;
    private LiveData<List<CommitmentWithMyStep>> commitmentWithSteps;

    public CommitmentRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        commitmentDao = db.commitmentDao();
        commitment = commitmentDao.getUserCommitment();
        commitmentWithSteps = commitmentDao.getCommitments();
    }


    public LiveData<List<UserCommitment>> getCommitment() {
        return commitment;
    }

    public LiveData<List<CommitmentWithMyStep>> getCommitmentWithSteps() {
        return commitmentWithSteps;
    }

    public void addCommitment(final MyCommitment commitment){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                commitmentDao.insert(commitment);
            }
        });
    }

    public void addStep(final MyStep... steps){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
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
}
