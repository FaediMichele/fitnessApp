package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.History;

import java.util.Objects;

public class HistoryWithExercise {
    @Embedded public History history;
    @Relation(parentColumn = "idExercise", entityColumn = "idExercise")
    public Exercise exercise;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryWithExercise that = (HistoryWithExercise) o;
        return Objects.equals(history, that.history) &&
                Objects.equals(exercise, that.exercise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(history, exercise);
    }
}
