package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyCommitment {
    @PrimaryKey public int idCommitment;
    public String name;
    public String desc;
    public int duration; // minutes
    public int idUser;
}
