package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"idUser", "cat"})
public class UserLevel {
    public int idUser;
    public String cat;
    public int PE;
    public int level;
}
