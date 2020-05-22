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
    //lista di string // nome dei vari check 200g insalata, 3k di corsa
    //

    public MyStep(long idCommitment, int num, String name, double incVal, String unitMeasure, double max, double progression) {
        this.idCommitment = idCommitment;
        this.num = num;
        this.name = name;
        this.incVal = incVal;
        this.unitMeasure = unitMeasure;
        this.max = max;// il valore di fine scala
        this.progression = progression;
    }
}
