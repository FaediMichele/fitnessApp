package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

@Entity(primaryKeys = {"idCourse", "idUser"}, foreignKeys ={@ForeignKey(entity = Course.class,
        parentColumns = "idCourse", childColumns = "idCourse", onDelete = ForeignKey.CASCADE)})
public class Review  implements MyEntity{
    public long idCourse;
    public long idUser;
    public double val;
    public String comment;
    public long date;


    public Review(long idCourse, long idUser, double val, String comment) {
        this.idCourse = idCourse;
        this.idUser = idUser;
        this.val = val;
        this.comment = comment;
    }

    public Review(JSONObject obj) throws JSONException {
        this.idCourse = obj.getLong("idCourse");
        this.idUser = obj.getLong("idUser");
        this.val = obj.getDouble("val");
        this.comment = obj.getString("comment");
        this.date = Util.isoFormatToTimestamp(obj.getString("date"));
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idCourse", idCourse);
        obj.put("idUser", idUser);
        obj.put("val", val);
        obj.put("comment", comment);
        obj.put("date", Util.dateToTimestamp(new Date(date)));
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return idCourse == review.idCourse &&
                idUser == review.idUser &&
                val == review.val &&
                Objects.equals(comment, review.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCourse, idUser, val, comment);
    }
}
