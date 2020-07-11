package com.example.crinaed.database.entity;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.crinaed.util.Category;
import com.example.crinaed.util.TypeOfStep;
import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * step
 */
@Entity
public class MyStep  implements MyEntity{
    @PrimaryKey  public long idMyStep;
    public long idCommitment;
    public String name;
    public String unitMeasure;
    public double max;
    public int repetitionDay; // day to reset the progression and save progress 1, 7, 30, 365
    public String type;//SOCIAL MENTAL PHYSICAL

    @TypeConverters(CategoryConverter.class)
    public Category category;

    public MyStep(long idMyStep, long idCommitment, String name, String unitMeasure, double max, int repetitionDay, String type, Category category) {
        this.idMyStep = idMyStep;
        this.idCommitment = idCommitment;
        this.name = name;
        this.unitMeasure = unitMeasure;
        this.max = max;// il valore di fine scala
        this.repetitionDay = repetitionDay;
        this.type = type;
        this.category=category;
    }
    public MyStep(JSONObject obj) throws JSONException {
        this.idMyStep = obj.getLong("idMyStep");
        this.idCommitment = obj.getLong("idCommitment");
        this.name = obj.getString("name");
        this.unitMeasure = obj.getString("unitMeasure");
        this.max = obj.getDouble("max");
        this.repetitionDay = obj.getInt("repetitionDay");
        this.type = obj.getString("type");
        this.category = Category.valueOf(obj.getString("category"));
    }

    public TypeOfStep getType(){
        return this.type.equals("progression")? TypeOfStep.PROGRESSION : TypeOfStep.CHECKLIST;
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
        obj.put("category", category);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyStep myStep = (MyStep) o;
        return idMyStep == myStep.idMyStep &&
                idCommitment == myStep.idCommitment &&
                Double.compare(myStep.max, max) == 0 &&
                repetitionDay == myStep.repetitionDay &&
                Objects.equals(name, myStep.name) &&
                Objects.equals(unitMeasure, myStep.unitMeasure) &&
                Objects.equals(type, myStep.type) &&
                category == myStep.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMyStep, idCommitment, name, unitMeasure, max, repetitionDay, type, category);
    }


    public static class CategoryConverter{
        @TypeConverter
        public Category fromInt(int value){
            return Category.values()[value];
        }
        @TypeConverter
        public int toInt(Category c){
            return c.ordinal();
        }
    }
}

