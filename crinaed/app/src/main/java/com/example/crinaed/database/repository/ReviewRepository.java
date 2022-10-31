package com.example.crinaed.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.ReviewDao;
import com.example.crinaed.database.entity.Review;
import com.example.crinaed.database.entity.join.user.UserReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ReviewRepository extends Repository{
    private ReviewDao reviewDao;

    public ReviewRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        reviewDao = db.reviewDao();
        setContext(context);
    }

    public LiveData<List<UserReview>> getUserReview(){
        return reviewDao.getUserReview();
    }

    public LiveData<List<Review>> getSchoolReview(long idSchool){
        return reviewDao.getSchoolReview(idSchool);
    }

    public Future<?> insert(final Review... reviews){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Long[]>() {
            @Override
            public Long[] call() {
                return reviewDao.insert(reviews);
            }
        });
    }

    public Future<?> update(final Review... reviews){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                reviewDao.update(reviews);
            }
        });
    }
    public Future<?> delete(final Review... reviews){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                reviewDao.delete(reviews);
            }
        });
    }

    @Override
    public Future<?> loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("Review");
        final List<Review> reviews = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            reviews.add(new Review(array.getJSONObject(i)));
        }
        return insert(reviews.toArray(new Review[0]));
    }

    @Override
    public Future<?> extractData(final JSONObject root) {
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    root.put("Review", listToJSONArray(reviewDao.getUserReviewList()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Future<?> deleteAll() {
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                reviewDao.deleteReview();
            }
        });
    }
}
