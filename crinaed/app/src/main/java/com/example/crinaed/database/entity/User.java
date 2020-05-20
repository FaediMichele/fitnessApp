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
}
