package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.crinaed.database.entity.FriendMessage;

import java.util.List;

@Dao
public interface FriendMessageDao {
    @Query("SELECT * FROM FriendMessage WHERE idFriendship = (:idFriendship) ORDER BY date")
    LiveData<List<FriendMessage>> getMessageByIdFriendship(long idFriendship);

    @Insert
    long[] insert(FriendMessage... friendMessages);

    @Update
    void update(FriendMessage friendMessage);

    @Delete
    void delete(FriendMessage... friendMessages);
}
