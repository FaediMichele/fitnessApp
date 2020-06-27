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
    public String email;
    public String address;
    public long idTrainer;

    public School(long idSchool, String name, String email, String address, long idTrainer) {
        this.idSchool = idSchool;
        this.name = name;
        this.email = email;
        this.address = address;
        this.idTrainer = idTrainer;
    }
    public School(JSONObject obj) throws JSONException {
        this.idSchool = obj.getLong("idSchool");
        this.name = obj.getString("name");
        this.email = obj.getString("email");
        this.address = obj.getString("address");
        this.idTrainer = obj.getLong("idTrainer");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idSchool", idSchool);
        obj.put("name", name);
        obj.put("email", email);
        obj.put("address", address);
        obj.put("idTrainer", idTrainer);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return idSchool == school.idSchool &&
                idTrainer == school.idTrainer &&
                Objects.equals(name, school.name) &&
                Objects.equals(email, school.email) &&
                Objects.equals(address, school.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSchool, name, email, address, idTrainer);
    }
}
