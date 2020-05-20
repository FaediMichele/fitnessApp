package com.example.crinaed.database.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"idCommitment", "num"})
public class MyStep {
    public long idCommitment;
    public int num;
    public String name;
    public double incVal;
    public String unitMeasure;
    public double max;
    public double progression;
}
