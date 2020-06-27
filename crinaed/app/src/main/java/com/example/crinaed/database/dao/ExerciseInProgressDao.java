package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;


import com.example.crinaed.database.entity.ExerciseInProgress;
import com.example.crinaed.database.entity.join.ExerciseInProgressWithExercise;

import java.util.List;

@Dao
public interface ExerciseInProgressDao {
    @Insert
    Long[] insert(ExerciseInProgress... exercise);

    @Update
    void update(ExerciseInProgress... exercise);

    @Delete
    void delete(ExerciseInProgress... exercise);

    @Transaction
    @Query("SELECT * FROM ExerciseInProgress")
    LiveData<List<ExerciseInProgressWithExercise>> get();

    @Query("SELECT * FROM ExerciseInProgress")
    List<ExerciseInProgress> getExerciseList();
}
