package com.example.crinaed.database.entity.join.user;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Friendship;
import com.example.crinaed.database.entity.User;

public class UserWithUser {
    @Embedded public Friendship friendship;

    @Relation(parentColumn = "idUser1", entityColumn = "idUser")
    public User user1;

    @Relation(parentColumn = "idUser2", entityColumn = "idUser")
    public User user2;

}
