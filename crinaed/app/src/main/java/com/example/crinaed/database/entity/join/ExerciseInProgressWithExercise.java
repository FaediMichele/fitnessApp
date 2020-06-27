package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.ExerciseInProgress;

import java.util.Objects;


public class ExerciseInProgressWithExercise {
    @Embedded public ExerciseInProgress exerciseInProgress;
    @Relation(parentColumn = "idExercise", entityColumn = "idExercise")
    public Exercise exercise;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseInProgressWithExercise that = (ExerciseInProgressWithExercise) o;
        return Objects.equals(exerciseInProgress, that.exerciseInProgress) &&
                Objects.equals(exercise, that.exercise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exerciseInProgress, exercise);
    }
}
