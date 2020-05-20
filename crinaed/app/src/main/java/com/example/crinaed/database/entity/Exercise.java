package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exercise {
    @PrimaryKey public long idExercise;
    public int level;
    public int PE;
    public int duration; // minutes
    public String name;
    public String desc;

    public long idCourse;
}
