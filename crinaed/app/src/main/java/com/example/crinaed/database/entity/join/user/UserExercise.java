package com.example.crinaed.database.entity.join.user;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.ExerciseInProgress;
import com.example.crinaed.database.entity.User;

import java.util.List;

public class UserExercise {
    @Embedded public User user;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<ExerciseInProgress> exerciseInProgresses;
}
