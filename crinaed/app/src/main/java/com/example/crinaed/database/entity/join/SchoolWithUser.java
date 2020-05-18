package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserSchoolCrossRef;

import java.util.List;

public class SchoolWithUser {
    @Embedded public School school;
    @Relation(parentColumn = "idSchool", entityColumn = "idUser", associateBy = @Junction(UserSchoolCrossRef.class))
    public List<User> users;
}
