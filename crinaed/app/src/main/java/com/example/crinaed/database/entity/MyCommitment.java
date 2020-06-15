package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class MyCommitment  implements MyEntity{
    @PrimaryKey public long idCommitment;
    public String name;
    public String desc;
    public long creationDate;
    public long idUser;

    public MyCommitment(long idCommitment, String name, String desc, long creationDate, long idUser) {
        this.idCommitment = idCommitment;
        this.name = name;
        this.desc = desc;
        this.creationDate = creationDate;
        this.idUser = idUser;
    }

    public MyCommitment(JSONObject obj) throws JSONException {
        this.idCommitment = obj.getLong("idCommitment");
        this.name = obj.getString("name");
        this.desc = obj.getString("desc");
        this.creationDate = Util.isoFormatToTimestamp(obj.getString("creationDate"));
        this.idUser = obj.getLong("idUser");
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idCommitment", idCommitment);
        obj.put("date", Util.timestampToIso(creationDate));
        obj.put("name", name);
        obj.put("desc", desc);
        obj.put("idUser", idUser);
        return obj;
    }
}
