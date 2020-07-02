package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.crinaed.database.entity.Friendship;
import com.example.crinaed.database.entity.join.user.UserWithUser;

import java.util.List;

@Dao
public interface FriendshipDao {
    @Query("SELECT * FROM Friendship WHERE idUser1 = (:idUser) OR idUser2 = (:idUser)")
    LiveData<List<Friendship>> getFriendshipByIdUser(long idUser);

    @Insert
    Long[] insert(Friendship... friendships);

    @Delete
    void delete(Friendship... friendships);

    @Query("SELECT * FROM Friendship")
    List<Friendship> getFriendshipList();

    @Transaction
    @Query("SELECT * FROM Friendship WHERE idUser2=(:idFriend)")
    LiveData<UserWithUser> getFriendshipByFriend(long idFriend);
}
