package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"idMyStep", "dateStart"},
        foreignKeys = {@ForeignKey(entity = MyStep.class, childColumns = "idMyStep", parentColumns = "idMyStep")})
public class MyStepDone {
    public long idMyStep;
    public long dateStart;
    public int result;

    public MyStepDone(long idMyStep, long dateStart, int result) {
        this.idMyStep = idMyStep;
        this.dateStart = dateStart;
        this.result = result;
    }
}
