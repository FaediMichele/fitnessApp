package com.example.crinaed.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crinaed.database.entity.FriendMessage;

import java.util.List;

@Dao
public interface FriendMessageDao {
    @Query("SELECT * FROM FriendMessage WHERE idFriendship = (:idFriendship)")
    List<FriendMessage> getMessageByIdFriendship(int idFriendship);

    @Insert
    void insertAll(FriendMessage... friendMessages);

}
