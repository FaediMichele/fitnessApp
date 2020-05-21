package com.example.crinaed.database.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"idUser", "idSchool"})
public class UserSchoolCrossRef {
    public long idUser;
    public long idSchool;

    public UserSchoolCrossRef(long idUser, long idSchool) {
        this.idUser = idUser;
        this.idSchool = idSchool;
    }
}
