package com.example.crinaed.database.entity.join.user;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserSchoolCrossRef;

import java.util.List;

public class UserIscription {
    @Embedded public User user;

    @Relation(parentColumn = "idUser", entityColumn = "idSchool", associateBy = @Junction(UserSchoolCrossRef.class))
    public List<School> schools;
}
