package com.example.crinaed.database.entity.join.user;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserLevel;

import java.util.List;
import java.util.Objects;

public class UserData {
    @Embedded
    public User user;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<UserLevel> levels;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(user, userData.user) &&
                Objects.equals(levels, userData.levels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, levels);
    }
}
