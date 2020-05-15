package com.example.crinaed.database.entity;

import androidx.room.PrimaryKey;

public class Course {
    @PrimaryKey public int idCourse;
    public String cat;
    public String name;
    public String desc;
    public int minimumLevel;

    public int idSchool;
}
