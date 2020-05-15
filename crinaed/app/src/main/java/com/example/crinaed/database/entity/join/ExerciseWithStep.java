package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.Step;

import java.util.List;

public class ExerciseWithStep {
    @Embedded Course course;
    @Relation(parentColumn = "idExercise", entityColumn = "idExercise")
    public List<Step> steps;
}