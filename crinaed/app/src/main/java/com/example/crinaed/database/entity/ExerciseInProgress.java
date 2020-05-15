package com.example.crinaed.database.entity;

import androidx.room.Entity;

import java.util.Date;

@Entity(primaryKeys = {"idUser", "idExercise"})
public class ExerciseInProgress {
    public int idUser;
    public int idExercise;
    public float progression;
    public int numStep;
    public Date lastEdit;
}
