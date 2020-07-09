package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserLevel;
import com.example.crinaed.database.entity.join.user.UserData;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    List<User> getUsersList();

    @Query("SELECT * FROM UserLevel")
    List<UserLevel> getLevelList();

    @Insert
    Long[] insert(User... users);


    @Insert
    Long[] insert(UserLevel... levels);

    @Update
    void update(User user);

    @Update
    void update(UserLevel level);

    @Query("DELETE FROM User")
    void deleteAll();

    @Transaction
    @Query("SELECT * FROM User WHERE idUser!=(:idUser)")
    LiveData<List<UserData>> getData(long idUser);
}
