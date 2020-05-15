package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.History;

public class HistoryWithExercise {
    @Embedded public History history;
    @Relation(parentColumn = "idExercise", entityColumn = "idExercise")
    public Exercise exercise;
}
