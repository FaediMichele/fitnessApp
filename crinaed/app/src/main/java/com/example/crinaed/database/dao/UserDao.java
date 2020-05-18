package com.example.crinaed.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserSchoolCrossRef;
import com.example.crinaed.database.entity.join.UserData;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM User WHERE idUser IN (:ids)")
    List<User> getById(int... ids);

    @Insert
    void insert(User user);

    @Query("DELETE FROM User")
    void deleteAll();

    @Transaction
    @Query("SELECT * FROM User")
    List<UserData> getData();

    @Insert
    void subscribeToSchool(UserSchoolCrossRef subscription);
}
