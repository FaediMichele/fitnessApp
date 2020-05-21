package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.crinaed.database.entity.Review;
import com.example.crinaed.database.entity.join.user.UserReview;

import java.util.List;

@Dao
public interface ReviewDao {

    @Insert
    Long[] insert(Review... reviews);

    @Update
    void update(Review... reviews);

    @Delete
    void delete(Review... reviews);

    @Transaction
    @Query("SELECT * FROM User")
    LiveData<List<UserReview>> getUserReview();

    @Transaction
    @Query("SELECT * FROM Review WHERE idSchool = (:idSchool)")
    LiveData<List<Review>> getSchoolReview(long idSchool);
}
