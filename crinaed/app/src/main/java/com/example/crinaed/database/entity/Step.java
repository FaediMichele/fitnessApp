package com.example.crinaed.database.entity;

import androidx.room.Entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

@Entity(primaryKeys = {"idExercise", "num"})
public class Step implements MyEntity {
    public long idExercise;
    public int num; // num of step for this exercise
    public String name;
    public String desc;
    public double incVal;
    public String unitMeasure;
    public double max;

    public Step(long idExercise, int num, String name, String desc, double incVal, String unitMeasure, double max) {
        this.idExercise = idExercise;
        this.num = num;
        this.name = name;
        this.desc = desc;
        this.incVal = incVal;
        this.unitMeasure = unitMeasure;
        this.max = max;
    }
    public Step(JSONObject obj) throws JSONException {
        this.idExercise = obj.getLong("idExercise");
        this.num = obj.getInt("num");
        this.name = obj.getString("name");
        this.desc = obj.getString("desc");
        this.incVal = obj.getDouble("incVal");
        this.unitMeasure = obj.getString("unitMeasure");
        this.max = obj.getDouble("max");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idExercise", idExercise);
        obj.put("num", num);
        obj.put("name", name);
        obj.put("desc", desc);
        obj.put("incVal", incVal);
        obj.put("unitMeasure", unitMeasure);
        obj.put("max", max);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return idExercise == step.idExercise &&
                num == step.num &&
                Double.compare(step.incVal, incVal) == 0 &&
                Double.compare(step.max, max) == 0 &&
                Objects.equals(name, step.name) &&
                Objects.equals(desc, step.desc) &&
                Objects.equals(unitMeasure, step.unitMeasure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExercise, num, name, desc, incVal, unitMeasure, max);
    }
}
