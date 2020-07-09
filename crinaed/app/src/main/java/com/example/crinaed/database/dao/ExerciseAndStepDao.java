package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.crinaed.database.entity.Exercise;

import java.util.List;

@Dao
public interface ExerciseAndStepDao {
    @Insert
    Long[] insert(Exercise... exercises);

    @Update
    void update(Exercise... exercises);

    @Delete
    void delete(Exercise... exercises);

    @Query("SELECT * FROM Exercise")
    List<Exercise> getExerciseList();

    @Query("SELECT * FROM Exercise WHERE idCourse=(:idCourse)")
    LiveData<List<Exercise>> getExercise(long idCourse);
}
