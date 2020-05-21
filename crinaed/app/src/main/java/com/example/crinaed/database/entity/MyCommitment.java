package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyCommitment {
    @PrimaryKey public long idCommitment;
    public String name;
    public String desc;
    public int duration; // minutes
    public long idUser;

    public MyCommitment(long idCommitment, String name, String desc, int duration, long idUser) {
        this.idCommitment = idCommitment;
        this.name = name;
        this.desc = desc;
        this.duration = duration;
        this.idUser = idUser;
    }
}
