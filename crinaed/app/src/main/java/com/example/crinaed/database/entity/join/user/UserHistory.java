package com.example.crinaed.database.entity.join.user;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.History;
import com.example.crinaed.database.entity.User;

import java.util.List;
import java.util.Objects;

public class UserHistory {
    @Embedded
    public User user;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<History> histories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHistory that = (UserHistory) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(histories, that.histories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, histories);
    }
}
