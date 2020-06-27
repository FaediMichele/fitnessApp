package com.example.crinaed.database.entity;

import androidx.room.Entity;

import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

@Entity(primaryKeys = {"idSchool", "idUser"})
public class Review  implements MyEntity{
    public long idSchool;
    public long idUser;
    public int val;
    public String comment;

    public Review(long idSchool, long idUser, int val, String comment) {
        this.idSchool = idSchool;
        this.idUser = idUser;
        this.val = val;
        this.comment = comment;
    }

    public Review(JSONObject obj) throws JSONException {
        this.idSchool = obj.getLong("idSchool");
        this.idUser = obj.getLong("idUser");
        this.val = obj.getInt("val");
        this.comment = obj.getString("comment");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idSchool", idSchool);
        obj.put("idUser", idUser);
        obj.put("val", val);
        obj.put("comment", comment);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return idSchool == review.idSchool &&
                idUser == review.idUser &&
                val == review.val &&
                Objects.equals(comment, review.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSchool, idUser, val, comment);
    }
}
