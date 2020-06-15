package com.example.crinaed.database.entity;

import androidx.room.Entity;

import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

@Entity(primaryKeys = {"idUser", "idCourse"})
public class CourseBought  implements MyEntity{
    public long idUser;
    public long idCourse;
    public int level;
    public long purchaseDate;

    public CourseBought(long idUser, long idCourse, int level, long purchaseDate) {
        this.idUser = idUser;
        this.idCourse = idCourse;
        this.level = level;
        this.purchaseDate = purchaseDate;
    }
    public CourseBought(JSONObject obj) throws JSONException {
        this.idUser = obj.getLong("idUser");
        this.idCourse = obj.getLong("idCourse");
        this.level = obj.getInt("level");
        this.purchaseDate = Util.isoFormatToTimestamp(obj.getString("purchaseDate"));
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idCourse", idCourse);
        obj.put("idUser", idUser);
        obj.put("level", level);
        obj.put("purchaseDate", Util.timestampToIso(purchaseDate));
        return obj;
    }
}
