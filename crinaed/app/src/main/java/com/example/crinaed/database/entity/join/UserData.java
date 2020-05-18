package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.crinaed.database.entity.CourseBought;
import com.example.crinaed.database.entity.ExerciseInProgress;
import com.example.crinaed.database.entity.History;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.Review;
import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserLevel;
import com.example.crinaed.database.entity.UserSchoolCrossRef;

import java.util.List;

public class UserData {
    @Embedded
    public User user;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<MyCommitment> commitments;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<CourseBought> courseBoughtList;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<ExerciseInProgress> exerciseInProgresses;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<History> histories;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<Review> reviews;

    @Relation(parentColumn = "idUser", entityColumn = "idSchool", associateBy = @Junction(UserSchoolCrossRef.class))
    public List<SchoolData> schools;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<UserLevel> levels;
}
