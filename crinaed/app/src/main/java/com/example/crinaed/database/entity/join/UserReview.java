package com.example.crinaed.database.entity.join;

import androidx.annotation.RestrictTo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Review;
import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.User;

import java.util.List;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.NO_ACTION;

@Entity(primaryKeys = {"idUser", "idSchool"}, foreignKeys = {
        @ForeignKey(entity = School.class, parentColumns = "idSchool", childColumns = "idSchool", onDelete = NO_ACTION),
        @ForeignKey(entity = User.class, parentColumns = "idUser", childColumns = "idUser", onDelete = CASCADE)
})
public class UserReview {
    public int idUser;
    public int idSchool;
    public int val;
    public String message;
}
