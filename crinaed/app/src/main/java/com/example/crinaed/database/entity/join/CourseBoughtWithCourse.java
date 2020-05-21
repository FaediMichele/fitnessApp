package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.CourseBought;

public class CourseBoughtWithCourse {
    @Embedded public CourseBought courseBought;

    @Relation(entityColumn = "idCourse", parentColumn = "idCourse")
    public Course course;
}
