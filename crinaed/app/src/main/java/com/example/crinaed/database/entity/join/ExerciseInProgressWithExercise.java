package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.ExerciseInProgress;


public class ExerciseInProgressWithExercise {
    @Embedded public ExerciseInProgress exerciseInProgress;
    @Relation(parentColumn = "idExercise", entityColumn = "idExercise")
    public Exercise exercise;
}
