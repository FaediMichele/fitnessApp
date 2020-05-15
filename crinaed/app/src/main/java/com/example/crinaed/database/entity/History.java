package com.example.crinaed.database.entity;

import androidx.room.Entity;

import java.util.Date;

@Entity(primaryKeys = {"idUser", "date"})
public class History {
    public int idUser;
    public Date date;
    public int idExercise;
}
