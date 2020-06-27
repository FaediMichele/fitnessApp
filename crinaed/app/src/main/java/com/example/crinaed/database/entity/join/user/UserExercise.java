package com.example.crinaed.database.entity.join.user;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.ExerciseInProgress;
import com.example.crinaed.database.entity.User;

import java.util.List;
import java.util.Objects;

public class UserExercise {
    @Embedded public User user;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<ExerciseInProgress> exerciseInProgresses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserExercise that = (UserExercise) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(exerciseInProgresses, that.exerciseInProgresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, exerciseInProgresses);
    }
}
