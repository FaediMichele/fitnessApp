package com.example.crinaed.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"idUser", "cat"})
public class UserLevel {
    public long idUser;
    @NonNull public String cat;
    public int PE;
    public int level;

    public UserLevel(long idUser, @NonNull String cat, int PE, int level) {
        this.idUser = idUser;
        this.cat = cat;
        this.PE = PE;
        this.level = level;
    }
}
