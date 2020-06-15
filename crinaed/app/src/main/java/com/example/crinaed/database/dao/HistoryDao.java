package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.crinaed.database.entity.History;
import com.example.crinaed.database.entity.join.HistoryWithExercise;
import com.example.crinaed.database.entity.join.user.UserHistory;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    Long[] insert(History... histories);

    @Delete
    void delete(History... histories);

    @Update
    void update(History... histories);

    @Transaction
    @Query("SELECT * FROM History")
    LiveData<List<HistoryWithExercise>> getUserHistory();

    @Query("SELECT * FROM History")
    List<History> getHistoryList();
}
