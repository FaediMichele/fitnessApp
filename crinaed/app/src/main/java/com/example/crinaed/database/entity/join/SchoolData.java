package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.School;

import java.util.List;

public class SchoolData {
    @Embedded
    public School school;

    @Relation(parentColumn = "idSchool", entityColumn = "idSchool")
    public List<Course> courses;
}
