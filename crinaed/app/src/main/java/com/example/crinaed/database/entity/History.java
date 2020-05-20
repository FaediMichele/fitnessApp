package com.example.crinaed.database.entity;

import androidx.room.Entity;

import java.util.Date;

@Entity(primaryKeys = {"idUser", "date"})
public class History {
    public long idUser;
    public Date date;
    public long idExercise;
}
