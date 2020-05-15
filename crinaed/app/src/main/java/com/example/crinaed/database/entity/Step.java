package com.example.crinaed.database.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"idExercise", "num"})
public class Step {
    public int idExercise;
    public int num; // num of step for this exercise
    public String name;
    public String desc;
    public float incVal;
    public String unitMeasure;
    public float max;
}
