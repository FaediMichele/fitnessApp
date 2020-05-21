package com.example.crinaed.database.entity;

import androidx.room.Entity;

import java.util.Date;

@Entity(primaryKeys = {"idUser", "idExercise"})
public class ExerciseInProgress {
    public long idUser;
    public long idExercise;
    public double progression;
    public int numStep;
    public long lastEdit;

    public ExerciseInProgress(long idUser, long idExercise, double progression, int numStep, long lastEdit) {
        this.idUser = idUser;
        this.idExercise = idExercise;
        this.progression = progression;
        this.numStep = numStep;
        this.lastEdit = lastEdit;
    }
}
