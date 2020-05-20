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
import com.example.crinaed.database.entity.join.user.UserData;
import com.example.crinaed.database.entity.join.user.UserInscription;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    long[] insert(User... users);

    @Insert
    long[] insert(UserSchoolCrossRef subscription);

    @Insert
    long[] insert(UserLevel... levels);

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
    LiveData<List<UserInscription>> getInscription();
}
