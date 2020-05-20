package com.example.crinaed.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.ReviewDao;
import com.example.crinaed.database.entity.History;
import com.example.crinaed.database.entity.Review;
import com.example.crinaed.database.entity.join.user.UserReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewRepository implements Repository{
    private ReviewDao reviewDao;

    public ReviewRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        reviewDao = db.reviewDao();
    }

    public LiveData<List<UserReview>> getUserReview(){
        return reviewDao.getUserReview();
    }

    public LiveData<List<UserReview>> getSchoolReview(long idSchool){
        return reviewDao.getSchoolReview(idSchool);
    }

    public void insert(final Review... reviews){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                reviewDao.insert(reviews);
            }
        });
    }

    public void update(final Review... reviews){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                reviewDao.update(reviews);
            }
        });
    }
    public void delete(final Review... reviews){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                reviewDao.delete(reviews);
            }
        });
    }

    @Override
    public void loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("Review");
        final List<Review> reviews = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            Review review = new Review();
            review.idSchool = obj.getLong("idSchool");
            review.idUser = obj.getLong("idUser");
            review.val = obj.getInt("val");
            review.comment = obj.getString("comment");
            reviews.add(review);
        }
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                reviewDao.insert((Review[]) reviews.toArray());
            }
        });
    }
}
