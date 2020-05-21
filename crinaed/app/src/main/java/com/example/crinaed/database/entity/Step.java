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

    public Step(long idExercise, int num, String name, String desc, double incVal, String unitMeasure, double max) {
        this.idExercise = idExercise;
        this.num = num;
        this.name = name;
        this.desc = desc;
        this.incVal = incVal;
        this.unitMeasure = unitMeasure;
        this.max = max;
    }
}
