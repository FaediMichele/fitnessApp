package com.example.crinaed.database.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.util.Lambda;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RepositoryManager {

    private List<Repository> repositories;
    private UserRepository userRepository;
    private SchoolRepository schoolRepository;
    private CommitmentRepository commitmentRepository;
    private ExerciseRepository exerciseRepository;
    private FriendRepository friendRepository;
    private ReviewRepository reviewRepository;

    public RepositoryManager(Context context){
        userRepository = new UserRepository(context);
        schoolRepository = new SchoolRepository(context);
        commitmentRepository = new CommitmentRepository(context);
        exerciseRepository = new ExerciseRepository(context);
        friendRepository = new FriendRepository(context);
        reviewRepository = new ReviewRepository(context);
        repositories = new ArrayList<>();
        addRepository(userRepository);
        addRepository(schoolRepository);
        addRepository(commitmentRepository);
        addRepository(exerciseRepository);
        addRepository(friendRepository);
        addRepository(reviewRepository);
    }

    private void addRepository(final Repository repository){
        repositories.add(repository);
    }

    public void loadNewData(AppDatabase db, String data) throws JSONException, ExecutionException, InterruptedException {
        db.clearAllTables();
        JSONObject json = new JSONObject(data);
        try {
            for (int i = 0; i < repositories.size(); i++) {
                repositories.get(i).loadData(json).get();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getData(final Lambda onDone){
        AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                final JSONObject root = new JSONObject();
                List<Future<?>> futureList = new ArrayList<>();
                for(Repository r : repositories){
                    futureList.add(r.extractData(root));
                }
                for(Future<?> r : futureList){
                    try {
                        r.get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                onDone.run(root);
            }
        });
    }

    public void deleteAll(final Lambda onDone) {
        AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=repositories.size()-1; i>=0; i--){
                        repositories.get(i).deleteAll().get();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                onDone.run(true);
            }
        });
    }

    public List<Repository> getAllRepository(){
        return Collections.unmodifiableList(repositories);
    }

    // getters
    public List<Repository> getRepositories() {
        return repositories;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public SchoolRepository getSchoolRepository() {
        return schoolRepository;
    }

    public CommitmentRepository getCommitmentRepository() {
        return commitmentRepository;
    }


    public ExerciseRepository getExerciseRepository() {
        return exerciseRepository;
    }

    public FriendRepository getFriendRepository() {
        return friendRepository;
    }

    public ReviewRepository getReviewRepository() {
        return reviewRepository;
    }


}
