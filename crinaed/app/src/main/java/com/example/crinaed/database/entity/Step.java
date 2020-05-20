package com.example.crinaed.database.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"idExercise", "num"})
public class Step {
    public long idExercise;
    public int num; // num of step for this exercise
    public String name;
    public String desc;
    public double incVal;
    public String unitMeasure;
    public double max;
}
