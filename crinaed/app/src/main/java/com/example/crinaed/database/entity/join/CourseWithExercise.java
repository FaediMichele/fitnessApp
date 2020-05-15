package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.Exercise;

import java.util.List;

public class CourseWithExercise {
    @Embedded public Course course;
    @Relation(parentColumn = "idCourse", entityColumn = "idCourse")
    public List<Exercise> exercises;
}
