package com.example.crinaed.database.entity.join.user;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserSchoolCrossRef;

import java.util.List;
import java.util.Objects;

public class UserInscription {
    @Embedded public User user;

    @Relation(parentColumn = "idUser", entityColumn = "idSchool", associateBy = @Junction(UserSchoolCrossRef.class))
    public List<School> schools;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInscription that = (UserInscription) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(schools, that.schools);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, schools);
    }
}
