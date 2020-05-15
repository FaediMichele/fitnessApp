package com.example.crinaed.database.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"idSchool", "idUser"})
public class Review {
    public int idSchool;
    public int idUser;
    public int val;
    public String comment;
}
