package com.example.crinaed.database.entity;

import androidx.room.Entity;

import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

@Entity(primaryKeys = {"idUser", "date"})
public class History  implements MyEntity{
    public long idUser;
    public long date;
    public long idExercise;

    public History(long idUser, long date, long idExercise) {
        this.idUser = idUser;
        this.date = date;
        this.idExercise = idExercise;
    }

    public History(JSONObject obj) throws JSONException {
        this.idUser = obj.getLong("idUser");
        this.date = Util.isoFormatToTimestamp(obj.getString("date"));
        this.idExercise = obj.getLong("idExercise");
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idUser", idUser);
        obj.put("date", Util.timestampToIso(date));
        obj.put("idExercise", idExercise);
        return obj;
    }
}
