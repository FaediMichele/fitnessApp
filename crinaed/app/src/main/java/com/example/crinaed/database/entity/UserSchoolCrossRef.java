package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.Index;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

@Entity(primaryKeys = {"idUser", "idSchool"}, indices = {@Index("idUser"), @Index("idSchool")})
public class UserSchoolCrossRef implements MyEntity {
    public long idUser;
    public long idSchool;

    public UserSchoolCrossRef(long idUser, long idSchool) {
        this.idUser = idUser;
        this.idSchool = idSchool;
    }
    public UserSchoolCrossRef(JSONObject obj) throws JSONException {
        this.idUser = obj.getLong("idUser");
        this.idSchool = obj.getLong("idSchool");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idUser", idUser);
        obj.put("idSchool", idSchool);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSchoolCrossRef that = (UserSchoolCrossRef) o;
        return idUser == that.idUser &&
                idSchool == that.idSchool;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idSchool);
    }
}
