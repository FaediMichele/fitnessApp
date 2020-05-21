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

    public Exercise(long idExercise, int level, int PE, int duration, String name, String desc, long idCourse) {
        this.idExercise = idExercise;
        this.level = level;
        this.PE = PE;
        this.duration = duration;
        this.name = name;
        this.desc = desc;
        this.idCourse = idCourse;
    }
}
