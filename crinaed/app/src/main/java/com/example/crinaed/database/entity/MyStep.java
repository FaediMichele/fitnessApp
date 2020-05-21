package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyStep {
    @PrimaryKey  public long idMyStep;
    public long idCommitment;
    public String name;
    public String unitMeasure;
    public double max;
    public int repetitionDay; // day to reset the progression and save progress
    public String type;

    public MyStep(long idMyStep, long idCommitment, String name, String unitMeasure, double max, int repetitionDay, String type) {
        this.idMyStep = idMyStep;
        this.idCommitment = idCommitment;
        this.name = name;
        this.unitMeasure = unitMeasure;
        this.max = max;
        this.repetitionDay = repetitionDay;
        this.type = type;
    }
}
