package com.example.crinaed.database.repository;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.database.dao.MyCommitmentDao;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.MyStepDone;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.database.entity.join.MyCommitmentWithMyMotivationalPhrase;
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.database.entity.join.MyStepWithMyStepDone;
import com.example.crinaed.util.Category;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Period;
import com.example.crinaed.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        setContext(context);
    }

    public LiveData<List<CommitmentWithMyStep>> getCommitmentWithSteps(Category category) {
        return commitmentDao.getCommitmentWithMyStep(category.ordinal());
    }

    public LiveData<List<CommitmentWithMyStep>> getCommitmentEndedWithSteps(Category category) {
        return commitmentDao.getCommitmentEndedWithMyStep(category.ordinal());
    }

    public LiveData<List<MyCommitment>> getCommitmentWithName(String name){
        return commitmentDao.getCommitmentWithName(name);
    }

    public LiveData<List<CommitmentWithMyStep>> getCommitmentNotArchived(){
        return commitmentDao.getCommitmentNotArchived();
    }

    public List<MyStepDoneWithMyStep> getStepHistoryList(final Category category){
        Future<List<MyStepDoneWithMyStep>> future;
        List<MyStepDoneWithMyStep> ret=null;
        try{
            future=AppDatabase.databaseWriteExecutor.submit(new Callable<List<MyStepDoneWithMyStep>>() {
                @Override
                public List<MyStepDoneWithMyStep> call() throws Exception {
                    return commitmentDao.getMyStepDoneWithMyStepWithCategory(category.ordinal());
                }
            });
            ret=future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;

    }


    public List<MyStepDoneWithMyStep> getStepHistoryList(final Category category, final Date date){
        Future<List<MyStepDoneWithMyStep>> future;
        List<MyStepDoneWithMyStep> ret=null;
        try{
            future=AppDatabase.databaseWriteExecutor.submit(new Callable<List<MyStepDoneWithMyStep>>() {
                @Override
                public List<MyStepDoneWithMyStep> call() throws Exception {
                    return commitmentDao.getMyStepDoneWithMyStepWithCategoryAndDataList(category.ordinal(), date.getTime());
                }
            });
            ret=future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public LiveData<List<MyStepDoneWithMyStep>> getStepHistory(final Category category, final Date date, final Period repetition){
        return commitmentDao.getMyStepDoneWithMyStepWithCategoryAndData(category.ordinal(), date.getTime(), repetition.getDay());
    }

    public LiveData<List<MyStepDoneWithMyStep>> getStepHistoryForCommitment(final long idCommitment, final Category category, final Date date, final Period repetition){
        return commitmentDao.getMyStepDoneWithMyStepWithCategoryAndData(idCommitment, category.ordinal(), date.getTime(), repetition.getDay());
    }

    public List<MyStepDoneWithMyStep> getStepHistoryList(final Category category, final Date date, final Period repetition){
        Future<List<MyStepDoneWithMyStep>> future;
        List<MyStepDoneWithMyStep> ret=null;
        try{
            future=AppDatabase.databaseWriteExecutor.submit(new Callable<List<MyStepDoneWithMyStep>>() {
                @Override
                public List<MyStepDoneWithMyStep> call() throws Exception {
                    Calendar midnight=new GregorianCalendar();
                    midnight.set(Calendar.HOUR_OF_DAY, 0);
                    midnight.set(Calendar.MINUTE, 0);
                    midnight.set(Calendar.SECOND, 0);
                    midnight.set(Calendar.MILLISECOND, 0);
                    Log.d("naed", "midnight: " + midnight.getTime());
                    return commitmentDao.getMyStepDoneWithMyStepWithCategoryAndDataList(category.ordinal(), date.getTime(), midnight.getTime().getTime(), repetition.getDay());
                }
            });
            ret=future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public LiveData<List<MyCommitmentWithMyMotivationalPhrase>> getMotivationalPhrase(){
        return commitmentDao.getMotivationalPhrase();
    }

    public LiveData<List<MyStepDoneWithMyStep>> getStepOnGoing(Category category, Period repetition){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, repetition.getDay()*-1);
        return commitmentDao.getLastMyStepDoneWithMyStep(category.ordinal(), calendar.getTime().getTime()-100, repetition.getDay());
    }

    public List<MyStepDoneWithMyStep> getStepOnGoingList(final Category category, final Period repetition){
        Future<List<MyStepDoneWithMyStep>> future;
        List<MyStepDoneWithMyStep> ret=null;
        try{
            future=AppDatabase.databaseWriteExecutor.submit(new Callable<List<MyStepDoneWithMyStep>>() {
                @Override
                public List<MyStepDoneWithMyStep> call() throws Exception {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_MONTH, repetition.getDay()*-1);
                    return commitmentDao.getLastMyStepDoneWithMyStepList(category.ordinal(), calendar.getTime().getTime(), repetition.getDay());
                }
            });
            ret=future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    public LiveData<List<CommitmentWithMyStep>> getAllCommitmentOnGoing(boolean ended){
        return commitmentDao.getAllCommitment(ended);
    }

    public LiveData<List<MyStepDoneWithMyStep>> getStepOnGoingByIdCommitment(long idCommitment, Period repetition){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, repetition.getDay()*-1);
        return commitmentDao.getLastMyStepDoneWithMyStepByIdCommitment(idCommitment, calendar.getTime().getTime()-100, repetition.getDay());
    }

    public LiveData<List<MyStepDoneWithMyStep>> getStepHistoryByIdCommitment(final long idCommitment, final Date date, final Period repetition){
        return commitmentDao.getMyStepDoneWithMyStepWithData(idCommitment, date.getTime(), repetition.getDay());
    }

    public Future<Pair<Long, Long[]>> insert(final MyCommitment commitment, final MyStep[] steps, final Lambda l){
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
                l.run();
                return ret;
            }
        });
    }

    // Not implemented in the server
    public Future<?> updateCommitment(final MyCommitment... commitment){
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

    public void updateMyStepDone(final MyStepDone... stepsDone){
        AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                commitmentDao.update(stepsDone);
            }
        });
    }

    public void createNewStepDone(){
        AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                /* TODO check this stuff */
                List<CommitmentWithMyStep> l = commitmentDao.getCommitmentWithMyStepList();
                long now = new Date().getTime();
                for(int i = 0; i < l.size(); i++){
                    CommitmentWithMyStep c = l.get(i);
                    for(int j = 0; j < c.steps.size(); j++){
                        MyStepWithMyStepDone s = c.steps.get(j);
                        MyStepDone last = commitmentDao.getLastStepDone(s.myStep.idMyStep);
                        if(last == null || now - last.dateStart >=  s.myStep.repetitionDay * 1000*60*60*24){
                            MyStepDone done = new MyStepDone(s.myStep.idMyStep, now, 0);
                            commitmentDao.insert(done);
                        }
                    }
                }
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
            final MyCommitment c=new MyCommitment(array.getJSONObject(i));
            commitmentList.add(c);
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
