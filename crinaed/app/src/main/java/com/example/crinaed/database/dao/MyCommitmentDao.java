package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.database.entity.join.user.UserCommitment;

import java.util.List;

@Dao
public interface MyCommitmentDao {
    @Insert
    long[] insert(MyCommitment... commitments);

    @Insert
    long[] insert(MyStep... mySteps);

    @Update
    void update(MyCommitment... commitments);

    @Update
    void update(MyStep... steps);

    @Delete
    void delete(MyCommitment... commitments);

    @Delete
    void delete(MyStep... steps);

    @Transaction
    @Query("SELECT * FROM MyCommitment")
    LiveData<List<CommitmentWithMyStep>> getCommitments();

    @Query("SELECT * FROM MyStep WHERE idCommitment = (:idCommitment)")
    List<MyStep> getStepByIdCommitment(long idCommitment);
}
