package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.crossRef.UserSchoolCrossRef;

import java.util.List;

public class UserWithSchool {
    @Embedded public User user;
    @Relation(parentColumn = "idUser", entityColumn = "idSchool", associateBy = @Junction(UserSchoolCrossRef.class))
    public List<School> schools;
}
