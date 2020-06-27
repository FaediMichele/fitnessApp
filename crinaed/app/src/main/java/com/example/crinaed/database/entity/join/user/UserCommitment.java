package com.example.crinaed.database.entity.join.user;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.User;

import java.util.List;
import java.util.Objects;

public class UserCommitment {
    @Embedded
    public User user;

    @Relation(parentColumn = "idUser", entityColumn = "idUser")
    public List<MyCommitment> commitments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCommitment that = (UserCommitment) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(commitments, that.commitments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, commitments);
    }
}
