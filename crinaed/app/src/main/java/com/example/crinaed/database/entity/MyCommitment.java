package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyCommitment {
    @PrimaryKey public long idCommitment;
    public String name;
    public String desc;
    public long creationDate;
    public long idUser;

    public MyCommitment(long idCommitment, String name, String desc, long creationDate, long idUser) {
        this.idCommitment = idCommitment;
        this.name = name;
        this.desc = desc;
        this.creationDate = creationDate;
        this.idUser = idUser;
    }
}
