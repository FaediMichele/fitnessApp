package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserLevel;
import com.example.crinaed.database.entity.UserSchoolCrossRef;
import com.example.crinaed.database.entity.join.user.UserCommitment;
import com.example.crinaed.database.entity.join.user.UserCourseBought;
import com.example.crinaed.database.entity.join.user.UserData;
import com.example.crinaed.database.entity.join.user.UserExercise;
import com.example.crinaed.database.entity.join.user.UserHistory;
import com.example.crinaed.database.entity.join.user.UserIscription;
import com.example.crinaed.database.entity.join.user.UserReview;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM User WHERE idUser IN (:ids)")
    List<User> getById(int... ids);

    @Insert
    void insert(User user);

    @Insert
    void insert(UserSchoolCrossRef subscription);

    @Insert
    void insert(UserLevel level);

    @Update
    void update(User user);

    @Update
    void update(UserLevel level);

    @Delete
    void delete(UserSchoolCrossRef subscription);

    @Query("DELETE FROM User")
    void deleteAll();

    @Transaction
    @Query("SELECT * FROM User")
    LiveData<List<UserData>> getData();

    @Transaction
    @Query("SELECT * FROM User")
    LiveData<List<UserExercise>> getExercise();

    @Transaction
    @Query("SELECT * FROM User")
    LiveData<List<UserIscription>> getInscription();

    @Transaction
    @Query("SELECT * FROM User")
    LiveData<List<UserReview>> getReview();



    @Transaction
    @Query("SELECT * FROM User")
    LiveData<List<UserHistory>> getHistory();
}
