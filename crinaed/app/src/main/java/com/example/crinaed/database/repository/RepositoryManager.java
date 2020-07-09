package com.example.crinaed.database.repository;

import android.app.Application;
import android.content.Context;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.DatabaseUtil;

import org.json.JSONArray;
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
    private CourseBoughtRepository courseBoughtRepository;
    private ExerciseAndStepRepository exerciseAndStepRepository;
    private ExerciseInProgressRepository exerciseInProgressRepository;
    private FriendRepository friendRepository;
    private HistoryRepository historyRepository;
    private ReviewRepository reviewRepository;


    private long idUser = -1;

    public RepositoryManager(Context context){
        userRepository = new UserRepository(context);
        schoolRepository = new SchoolRepository(context);
        commitmentRepository = new CommitmentRepository(context);
        courseBoughtRepository = new CourseBoughtRepository(context);
        exerciseAndStepRepository = new ExerciseAndStepRepository(context);
        exerciseInProgressRepository = new ExerciseInProgressRepository(context);
        friendRepository = new FriendRepository(context);
        historyRepository = new HistoryRepository(context);
        reviewRepository = new ReviewRepository(context);
        repositories = new ArrayList<>();
        addRepository(userRepository);
        addRepository(schoolRepository);
        addRepository(commitmentRepository);
        addRepository(courseBoughtRepository);
        addRepository(exerciseAndStepRepository);
        addRepository(exerciseInProgressRepository);
        addRepository(friendRepository);
        addRepository(historyRepository);
        addRepository(reviewRepository);
    }

    private void addRepository(final Repository repository){
        repositories.add(repository);
    }

    public void loadNewData(AppDatabase db, String data) throws JSONException, ExecutionException, InterruptedException {
        db.clearAllTables();
        JSONObject json = new JSONObject(data);
        idUser = json.getJSONArray("User").getJSONObject(0).getLong("idUser");
        try {
            for (int i = 0; i < repositories.size(); i++) {
                repositories.get(i).loadData(json).get();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public JSONObject getData(){
        JSONObject root = new JSONObject();
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
        return root;
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

    public CourseBoughtRepository getCourseBoughtRepository() {
        return courseBoughtRepository;
    }

    public ExerciseAndStepRepository getExerciseAndStepRepository() {
        return exerciseAndStepRepository;
    }

    public ExerciseInProgressRepository getExerciseInProgressRepository() {
        return exerciseInProgressRepository;
    }

    public FriendRepository getFriendRepository() {
        return friendRepository;
    }

    public HistoryRepository getHistoryRepository() {
        return historyRepository;
    }

    public ReviewRepository getReviewRepository() {
        return reviewRepository;
    }

    public long getIdUser() {
        return idUser;
    }
}
