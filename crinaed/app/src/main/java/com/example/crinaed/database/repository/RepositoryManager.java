package com.example.crinaed.database.repository;

import android.app.Application;
import android.content.Context;

import com.example.crinaed.database.AppDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        for(int i = 0; i < repositories.size(); i++){
            repositories.get(i).loadData(json).get();
        }
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
}
