package com.example.crinaed.database.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"idSchool", "idUser"})
public class Review {
    public long idSchool;
    public long idUser;
    public int val;
    public String comment;
}
