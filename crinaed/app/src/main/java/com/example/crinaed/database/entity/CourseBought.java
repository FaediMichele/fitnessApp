package com.example.crinaed.database.entity;

import androidx.room.Entity;

import java.util.Date;

@Entity(primaryKeys = {"idUser", "idCourse"})
public class CourseBought {
    public long idUser;
    public long idCourse;
    public int level;
    public float progression;
    public Date purchaseDate;
}
