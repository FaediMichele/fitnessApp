package com.example.crinaed.database.entity.join.user;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserLevel;

import java.util.List;

public class UserData {
    @Embedded
    public User user;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<UserLevel> levels;
}
