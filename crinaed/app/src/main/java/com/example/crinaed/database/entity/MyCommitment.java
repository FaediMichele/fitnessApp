package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

@Entity
public class MyCommitment  implements MyEntity{
    @PrimaryKey public long idCommitment;
    public String name;
    public String desc;
    public long creationDate;
    public long endDate;
    public boolean ended;
    public long idUser;
    public String image;
    public boolean imageDownloaded=false;

    public MyCommitment(long idCommitment, String name, String desc, long creationDate, long idUser) {
        this.idCommitment = idCommitment;
        this.name = name;
        this.desc = desc;
        this.creationDate = creationDate;
        this.endDate=0;
        this.idUser = idUser;
    }

    public MyCommitment(JSONObject obj) throws JSONException {
        this.idCommitment = obj.getLong("idCommitment");
        this.name = obj.getString("name");
        this.desc = obj.getString("desc");
        this.creationDate = Util.isoFormatToTimestamp(obj.getString("creationDate"));
        this.idUser = obj.getLong("idUser");
        String endDate=obj.getString("endDate");
        if(endDate.equals("0")){
            ended=false;
            this.endDate="0";
        } else{
            this.endDate = Util.isoFormatToTimestamp("endDate");
            ended=true;
        }

    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idCommitment", idCommitment);
        obj.put("creationDate", Util.timestampToIso(creationDate));
        obj.put("name", name);
        obj.put("desc", desc);
        obj.put("idUser", idUser);
        obj.put("endDate", endDate);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyCommitment that = (MyCommitment) o;
        return idCommitment == that.idCommitment &&
                creationDate == that.creationDate &&
                idUser == that.idUser &&
                Objects.equals(name, that.name) &&
                Objects.equals(desc, that.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCommitment, name, desc, creationDate, idUser);
    }
}
