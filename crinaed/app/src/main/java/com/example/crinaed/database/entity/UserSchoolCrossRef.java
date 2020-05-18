package com.example.crinaed.database.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"idUser", "idSchool"})
public class UserSchoolCrossRef {
    public int idUser;
    public int idSchool;
}
