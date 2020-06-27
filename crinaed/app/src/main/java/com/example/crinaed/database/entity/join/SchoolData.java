package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.School;

import java.util.List;
import java.util.Objects;

public class SchoolData {
    @Embedded
    public School school;

    @Relation(parentColumn = "idSchool", entityColumn = "idSchool")
    public List<Course> courses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolData that = (SchoolData) o;
        return Objects.equals(school, that.school) &&
                Objects.equals(courses, that.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(school, courses);
    }
}
