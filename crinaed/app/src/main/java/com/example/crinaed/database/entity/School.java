package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "idUser", childColumns = "idTrainer"))
public class School {
    @PrimaryKey
    public long idSchool;
    public String name;
    public String email;
    public String address;
    public long idTrainer;
}
