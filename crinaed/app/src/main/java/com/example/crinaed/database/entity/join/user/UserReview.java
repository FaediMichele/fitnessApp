package com.example.crinaed.database.entity.join.user;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Review;
import com.example.crinaed.database.entity.User;

import java.util.List;

public class UserReview {

    @Embedded public User user;
    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<Review> reviews;
}
