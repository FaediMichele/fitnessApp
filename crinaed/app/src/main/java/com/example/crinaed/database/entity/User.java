package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {
    @PrimaryKey
    public long idUser;
    public String firstname;
    public String surname;
    public String email;
    public String hashPassword;

    public User(long idUser, String firstname, String surname, String email, String hashPassword) {
        this.idUser = idUser;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.hashPassword = hashPassword;
    }
}
