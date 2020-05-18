package com.example.crinaed.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crinaed.database.entity.Friendship;

import java.util.List;

@Dao
public interface FriendshipDao {
    @Query("SELECT * FROM Friendship WHERE idUser1 = (:idUser) OR idUser2 = (:idUser)")
    List<Friendship> getFriendshipByIdUser(int idUser);

    @Insert
    void insertAll(Friendship... friendships);
}
