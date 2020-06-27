package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.CourseBought;

import java.util.Objects;

public class CourseBoughtWithCourse {
    @Embedded public CourseBought courseBought;

    @Relation(entityColumn = "idCourse", parentColumn = "idCourse")
    public Course course;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseBoughtWithCourse that = (CourseBoughtWithCourse) o;
        return Objects.equals(courseBought, that.courseBought) &&
                Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseBought, course);
    }
}
