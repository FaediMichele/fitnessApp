package com.example.crinaed.database.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

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

public class ReviewRepository implements Repository{
    private ReviewDao reviewDao;

    public ReviewRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        reviewDao = db.reviewDao();
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
            JSONObject obj = array.getJSONObject(i);
            Review review = new Review(obj.getLong("idSchool"), obj.getLong("idUser"), obj.getInt("val"), obj.getString("comment"));
            reviews.add(review);
        }
        return insert(reviews.toArray(new Review[0]));
    }
}
