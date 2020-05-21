package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(primaryKeys = {"idUser", "idSchool"}, indices = {@Index("idUser"), @Index("idSchool")})
public class UserSchoolCrossRef {
    public long idUser;
    public long idSchool;

    public UserSchoolCrossRef(long idUser, long idSchool) {
        this.idUser = idUser;
        this.idSchool = idSchool;
    }
}
