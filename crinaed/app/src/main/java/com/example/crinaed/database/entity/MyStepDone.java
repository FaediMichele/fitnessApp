package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

@Entity(primaryKeys = {"idMyStep", "dateStart"},
        foreignKeys = {@ForeignKey(entity = MyStep.class, childColumns = "idMyStep", parentColumns = "idMyStep")})
public class MyStepDone  implements MyEntity{
    public long idMyStep;
    public long dateStart;
    public int result;

    public MyStepDone(long idMyStep, long dateStart, int result) {
        this.idMyStep = idMyStep;
        this.dateStart = dateStart;
        this.result = result;
    }
    public MyStepDone(JSONObject obj) throws JSONException {
        this.idMyStep = obj.getLong("idMyStep");
        this.dateStart = Util.isoFormatToTimestamp(obj.getString("dateStart"));
        this.result = obj.getInt("result");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idMyStep", idMyStep);
        obj.put("dateStart", Util.timestampToIso(dateStart));
        obj.put("result", result);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyStepDone that = (MyStepDone) o;
        return idMyStep == that.idMyStep &&
                dateStart == that.dateStart &&
                result == that.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMyStep, dateStart, result);
    }
}
