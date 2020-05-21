package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.CourseBought;
import com.example.crinaed.database.entity.Exercise;

public class CourseBoughtWithExercise {
    @Embedded public CourseBought courseBought;
    @Relation(parentColumn = "idExercise", entityColumn = "idExercise")
    public Exercise exercise;
}
