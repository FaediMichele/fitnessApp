package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.Index;

import org.json.JSONException;
import org.json.JSONObject;

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
}
