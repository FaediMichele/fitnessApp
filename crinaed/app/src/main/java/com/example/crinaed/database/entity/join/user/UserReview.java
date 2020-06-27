package com.example.crinaed.database.entity.join.user;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Review;
import com.example.crinaed.database.entity.User;

import java.util.List;
import java.util.Objects;

public class UserReview {

    @Embedded public User user;
    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<Review> reviews;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserReview that = (UserReview) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(reviews, that.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, reviews);
    }
}
