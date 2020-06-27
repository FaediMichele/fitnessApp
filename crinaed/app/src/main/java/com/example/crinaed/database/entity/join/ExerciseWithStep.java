package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.Step;

import java.util.List;
import java.util.Objects;

public class ExerciseWithStep {
    @Embedded public Exercise course;

    @Relation(parentColumn = "idExercise", entityColumn = "idExercise")
    public List<Step> steps;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseWithStep that = (ExerciseWithStep) o;
        return Objects.equals(course, that.course) &&
                Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, steps);
    }
}
