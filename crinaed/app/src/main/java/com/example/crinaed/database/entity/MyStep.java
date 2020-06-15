package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class MyStep  implements MyEntity{
    @PrimaryKey  public long idMyStep;
    public long idCommitment;
    public String name;
    public String unitMeasure;
    public double max;
    public int repetitionDay; // day to reset the progression and save progress
    public String type;

    public MyStep(long idMyStep, long idCommitment, String name, String unitMeasure, double max, int repetitionDay, String type) {
        this.idMyStep = idMyStep;
        this.idCommitment = idCommitment;
        this.name = name;
        this.unitMeasure = unitMeasure;
        this.max = max;
        this.repetitionDay = repetitionDay;
        this.type = type;
    }
    public MyStep(JSONObject obj) throws JSONException {
        this.idMyStep = obj.getLong("idMyStep");
        this.idCommitment = obj.getLong("idCommitment");
        this.name = obj.getString("name");
        this.unitMeasure = obj.getString("unitMeasure");
        this.max = obj.getDouble("max");
        this.repetitionDay = obj.getInt("repetitionDay");
        this.type = obj.getString("type");
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idMyStep", idMyStep);
        obj.put("idCommitment", idCommitment);
        obj.put("name", name);
        obj.put("unitMeasure", unitMeasure);
        obj.put("max", max);
        obj.put("repetitionDay", repetitionDay);
        obj.put("type", type);
        return obj;
    }
}
