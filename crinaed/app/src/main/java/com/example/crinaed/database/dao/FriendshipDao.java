package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crinaed.database.entity.Friendship;

import java.util.List;

@Dao
public interface FriendshipDao {
    @Query("SELECT * FROM Friendship WHERE idUser1 = (:idUser) OR idUser2 = (:idUser)")
    LiveData<List<Friendship>> getFriendshipByIdUser(long idUser);

    @Insert
    Long[] insert(Friendship... friendships);

    @Delete
    void delete(Friendship... friendships);


}
