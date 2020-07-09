package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "idUser", childColumns = "idTrainer"),
indices = @Index("idTrainer"))
public class School implements MyEntity {
    @PrimaryKey
    public long idSchool;
    public String name;
    public long idTrainer;
    public String image;
    public boolean imageDownloaded=false;
    public String desc;

    public School(long idSchool, String name, long idTrainer, String image, boolean imageDownloaded, String desc) {
        this.idSchool = idSchool;
        this.name = name;
        this.idTrainer = idTrainer;
        this.image = image;
        this.imageDownloaded = imageDownloaded;
        this.desc = desc;
    }

    public School(JSONObject obj) throws JSONException {
        this.idSchool = obj.getLong("idSchool");
        this.name = obj.getString("name");
        this.idTrainer = obj.getLong("idTrainer");
        this.image = obj.getString("image");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return idSchool == school.idSchool &&
                idTrainer == school.idTrainer &&
                imageDownloaded == school.imageDownloaded &&
                Objects.equals(name, school.name) &&
                Objects.equals(image, school.image) &&
                Objects.equals(desc, school.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSchool, name, idTrainer, image, imageDownloaded, desc);
    }
}
