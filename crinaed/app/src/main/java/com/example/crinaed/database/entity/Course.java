package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class Course implements MyEntity{
    @PrimaryKey public long idCourse;
    public String cat;
    public String name;
    public String desc;
    public int minimumLevel;

    public long idSchool;

    public Course(long idCourse, String cat, String name, String desc, int minimumLevel, long idSchool){
        this.idSchool = idSchool;
        this.idCourse = idCourse;
        this.cat = cat;
        this.name = name;
        this.desc = desc;
        this.minimumLevel = minimumLevel;
    }

    public Course(JSONObject obj) throws JSONException {
        idCourse = obj.getLong("idCourse");
        cat = obj.getString("cat");
        name = obj.getString("name");
        desc = obj.getString("desc");
        minimumLevel = obj.getInt("minimumLevel");
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idCourse", idCourse);
        obj.put("cat", cat);
        obj.put("name", name);
        obj.put("desc", desc);
        obj.put("minimumLevel", minimumLevel);
        return obj;
    }
}
