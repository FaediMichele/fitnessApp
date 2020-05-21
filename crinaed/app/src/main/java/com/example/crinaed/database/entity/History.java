package com.example.crinaed.database.entity;

import androidx.room.Entity;

import java.util.Date;

@Entity(primaryKeys = {"idUser", "date"})
public class History {
    public long idUser;
    public long date;
    public long idExercise;

    public History(long idUser, long date, long idExercise) {
        this.idUser = idUser;
        this.date = date;
        this.idExercise = idExercise;
    }
}
