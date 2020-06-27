package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return idCourse == course.idCourse &&
                minimumLevel == course.minimumLevel &&
                idSchool == course.idSchool &&
                Objects.equals(cat, course.cat) &&
                Objects.equals(name, course.name) &&
                Objects.equals(desc, course.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCourse);
    }

}
