package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.User;

import java.util.List;
import java.util.Objects;

public class SchoolData {
    @Embedded
    public School school;

    @Relation(parentColumn = "idTrainer", entityColumn = "idUser")
    public User user;

    @Relation(parentColumn = "idSchool", entityColumn = "idSchool")
    public List<CourseWithExercise> courseData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolData that = (SchoolData) o;
        return Objects.equals(school, that.school) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(school, user);
    }
}
