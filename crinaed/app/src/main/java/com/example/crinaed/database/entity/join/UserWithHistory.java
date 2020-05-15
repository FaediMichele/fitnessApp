package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.History;
import com.example.crinaed.database.entity.User;

import java.util.List;

public class UserWithHistory {
    @Embedded public User user;
    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<History> histories;
}
