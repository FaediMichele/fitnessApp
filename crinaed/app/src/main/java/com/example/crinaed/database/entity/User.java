package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

@Entity(tableName = "User")
public class User implements MyEntity {
    @PrimaryKey
    public long idUser;
    public String firstname;
    public String surname;
    public String email;
    public String hashPassword;

    public User(long idUser, String firstname, String surname, String email, String hashPassword) {
        this.idUser = idUser;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.hashPassword = hashPassword;
    }

    public User(JSONObject obj) throws JSONException {
        this.idUser = obj.getLong("idUser");
        this.firstname = obj.getString("firstname");
        this.surname = obj.getString("surname");
        this.email = obj.getString("email");
        this.hashPassword = obj.getString("hashPassword");
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idUser", idUser);
        obj.put("firstname", firstname);
        obj.put("surname", surname);
        obj.put("email", email);
        obj.put("hashPassword", hashPassword);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return idUser == user.idUser &&
                Objects.equals(firstname, user.firstname) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(email, user.email) &&
                Objects.equals(hashPassword, user.hashPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, firstname, surname, email, hashPassword);
    }
}
