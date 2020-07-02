package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyMotivationalPhrase;

import java.util.List;


public class MyCommitmentWithMyMotivationalPhrase {
    @Embedded
    public MyCommitment commitment;
    @Relation(parentColumn = "idCommitment", entityColumn = "idCommitment")
    public List<MyMotivationalPhrase> phrase;
}
