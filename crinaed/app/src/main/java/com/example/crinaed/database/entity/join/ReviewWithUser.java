package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.Review;
import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.User;

import java.util.List;

public class ReviewWithUser {

    @Embedded
    public Review review;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public User user;
}
