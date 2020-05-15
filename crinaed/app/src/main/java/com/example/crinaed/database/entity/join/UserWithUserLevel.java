package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserLevel;

import java.util.List;

public class UserWithUserLevel {
    @Embedded public User user;
    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<UserLevel> levels;
}
