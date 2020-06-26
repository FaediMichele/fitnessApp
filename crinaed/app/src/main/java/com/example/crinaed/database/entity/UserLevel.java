package com.example.crinaed.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import org.json.JSONException;
import org.json.JSONObject;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"idUser", "cat"})
public class UserLevel implements MyEntity {
    public long idUser;
    @NonNull public String cat;
    public int PE;
    public int level;

    public UserLevel(long idUser, @NonNull String cat, int PE, int level) {
        this.idUser = idUser;
        this.cat = cat;
        this.PE = PE;
        this.level = level;
    }
    public UserLevel(JSONObject obj) throws JSONException {
        this.idUser = obj.getLong("idUser");
        this.cat = obj.getString("cat");
        this.PE = obj.getInt("PE");
        this.level = obj.getInt("level");
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idUser", idUser);
        obj.put("cat", cat);
        obj.put("PE", PE);
        obj.put("level", level);
        return obj;
    }
}
