package com.example.crinaed.database.entity;

import androidx.room.Entity;

import java.util.Date;

@Entity(primaryKeys = {"idUser", "idCourse"})
public class CourseBought {
    public int idUser;
    public int idCourse;
    public int level;
    public float progression;
    public Date purchaseDate;
}
