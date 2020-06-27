package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.Exercise;

import java.util.List;
import java.util.Objects;

public class CourseWithExercise {
    @Embedded public Course course;
    @Relation(parentColumn = "idCourse", entityColumn = "idCourse")
    public List<Exercise> exercises;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseWithExercise that = (CourseWithExercise) o;
        return Objects.equals(course, that.course) &&
                Objects.equals(exercises, that.exercises);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, exercises);
    }
}
