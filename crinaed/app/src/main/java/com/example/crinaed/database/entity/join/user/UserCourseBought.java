package com.example.crinaed.database.entity.join.user;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.CourseBought;
import com.example.crinaed.database.entity.User;

import java.util.List;
import java.util.Objects;

public class UserCourseBought {
    @Embedded
    public User user;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<CourseBought> courseBoughtList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCourseBought that = (UserCourseBought) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(courseBoughtList, that.courseBoughtList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, courseBoughtList);
    }
}
