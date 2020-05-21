package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.Step;
import com.example.crinaed.database.entity.join.ExerciseWithStep;

import java.util.List;

@Dao
public interface ExerciseAndStepDao {
    @Insert
    Long[] insert(Exercise... exercises);

    @Insert
    Long[] insert(Step... steps);

    @Update
    void update(Exercise... exercises);

    @Update
    void update(Step... steps);

    @Delete
    void delete(Exercise... exercises);

    @Delete
    void delete(Step... steps);

    @Transaction
    @Query("SELECT * FROM Exercise WHERE idCourse = (:idCourse)")
    LiveData<List<ExerciseWithStep>> get(long idCourse);

    @Query("SELECT * FROM Step WHERE idExercise = (:idExercise)")
    List<Step> getStepByIdExercise(long idExercise);
}
