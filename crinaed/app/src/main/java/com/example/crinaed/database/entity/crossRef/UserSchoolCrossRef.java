package com.example.crinaed.database.entity.crossRef;

import androidx.room.Entity;

@Entity(primaryKeys = {"idUser", "idSchool"})
public class UserSchoolCrossRef {
    public int idUser;
    public int idSchool;
}
