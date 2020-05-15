package com.example.crinaed.database.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"idCommitment", "num"})
public class MyStep {
    public int idCommitment;
    public int num;
    public String name;
    public float incVal;
    public String unitMeasure;
    public float max;
    public float progression;
}
