package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

@Entity
public class Exercise  implements MyEntity{
    @PrimaryKey public long idExercise;
    public int level;
    public int PE;
    public int duration; // minutes
    public String name;
    public String desc;
    public String video;
    public boolean videoDownloaded;

    public long idCourse;

    public Exercise(long idExercise, int level, int PE, int duration, String name, String desc, long idCourse) {
        this.idExercise = idExercise;
        this.level = level;
        this.PE = PE;
        this.duration = duration;
        this.name = name;
        this.desc = desc;
        this.idCourse = idCourse;
        videoDownloaded=false;
    }

    public Exercise(JSONObject obj) throws JSONException {
        this.idExercise = obj.getLong("idExercise");
        this.level = obj.getInt("level");
        this.PE = obj.getInt("PE");
        this.duration = obj.getInt("duration");
        this.name = obj.getString("name");
        this.desc = obj.getString("desc");
        this.idCourse = obj.getLong("idCourse");
        this.video = obj.getString("video");
        videoDownloaded=false;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idExercise", idExercise);
        obj.put("level", level);
        obj.put("PE", PE);
        obj.put("duration", duration);
        obj.put("name", name);
        obj.put("desc", desc);
        obj.put("idCourse", idCourse);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return idExercise == exercise.idExercise &&
                level == exercise.level &&
                PE == exercise.PE &&
                duration == exercise.duration &&
                idCourse == exercise.idCourse &&
                Objects.equals(name, exercise.name) &&
                Objects.equals(desc, exercise.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExercise, level, PE, duration, name, desc, idCourse);
    }
}
