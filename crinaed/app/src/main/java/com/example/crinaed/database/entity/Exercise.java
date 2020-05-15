package com.example.crinaed.database.entity;

import androidx.room.PrimaryKey;

public class Exercise {
    @PrimaryKey public int idExercise;
    public int level;
    public int PE;
    public int duration; // minutes
    public String name;
    public String desc;

    public int idCourse;
}
