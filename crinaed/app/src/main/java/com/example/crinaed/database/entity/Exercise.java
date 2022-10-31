package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

@Entity(foreignKeys ={@ForeignKey(entity = Course.class,
        parentColumns = "idCourse", childColumns = "idCourse", onDelete = ForeignKey.CASCADE)},
        indices = {@Index("idCourse")})
public class Exercise  implements MyEntity{
    @PrimaryKey public long idExercise;
    public String name;
    public String desc;
    public String image;
    public boolean imageDownloaded;
    public String video;
    public boolean videoDownloaded;
    public String importData;

    public long idCourse;

    public Exercise(long idExercise, String name, String desc, String image, boolean imageDownloaded, String video, boolean videoDownloaded, String importData, long idCourse) {
        this.idExercise = idExercise;
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.imageDownloaded = imageDownloaded;
        this.video = video;
        this.videoDownloaded = videoDownloaded;
        this.importData = importData;
        this.idCourse = idCourse;
    }

    public Exercise(JSONObject obj) throws JSONException {
        this.idExercise = obj.getLong("idExercise");
        this.name = obj.getString("name");
        this.desc = obj.getString("desc");
        this.idCourse = obj.getLong("idCourse");
        this.video = obj.getString("video");
        this.importData = obj.getString("importData");
        videoDownloaded=false;
        imageDownloaded=false;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return idExercise == exercise.idExercise &&
                imageDownloaded == exercise.imageDownloaded &&
                videoDownloaded == exercise.videoDownloaded &&
                idCourse == exercise.idCourse &&
                Objects.equals(name, exercise.name) &&
                Objects.equals(desc, exercise.desc) &&
                Objects.equals(image, exercise.image) &&
                Objects.equals(video, exercise.video) &&
                Objects.equals(importData, exercise.importData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExercise, name, desc, image, imageDownloaded, video, videoDownloaded, importData, idCourse);
    }
}
