package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {
    @PrimaryKey
    public int idUser;
    public String firstname;
    public String surname;
    public String email;
    public String hashPassword;
}
