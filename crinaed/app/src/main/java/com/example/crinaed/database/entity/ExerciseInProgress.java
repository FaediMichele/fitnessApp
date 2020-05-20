package com.example.crinaed.database.entity;

import androidx.room.Entity;

import java.util.Date;

@Entity(primaryKeys = {"idUser", "idExercise"})
public class ExerciseInProgress {
    public long idUser;
    public long idExercise;
    public double progression;
    public int numStep;
    public Date lastEdit;
}
