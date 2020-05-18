package com.example.crinaed.database.entity.join.user;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.CourseBought;
import com.example.crinaed.database.entity.User;

import java.util.List;

public class UserCourseBought {
    @Embedded
    public User user;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<CourseBought> courseBoughtList;
}
