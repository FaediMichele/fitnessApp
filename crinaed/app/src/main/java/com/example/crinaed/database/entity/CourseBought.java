package com.example.crinaed.database.entity;

import androidx.room.Entity;

import java.util.Date;

@Entity(primaryKeys = {"idUser", "idCourse"})
public class CourseBought {
    public long idUser;
    public long idCourse;
    public int level;
    public long purchaseDate;

    public CourseBought(long idUser, long idCourse, int level, long purchaseDate) {
        this.idUser = idUser;
        this.idCourse = idCourse;
        this.level = level;
        this.purchaseDate = purchaseDate;
    }
}
