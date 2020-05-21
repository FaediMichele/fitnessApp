package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {
    @PrimaryKey public long idCourse;
    public String cat;
    public String name;
    public String desc;
    public int minimumLevel;

    public long idSchool;

    public Course(long idCourse, String cat, String name, String desc, int minimumLevel, long idSchool){
        this.idSchool = idSchool;
        this.idCourse = idCourse;
        this.cat = cat;
        this.name = name;
        this.desc = desc;
        this.minimumLevel = minimumLevel;
    }
}
