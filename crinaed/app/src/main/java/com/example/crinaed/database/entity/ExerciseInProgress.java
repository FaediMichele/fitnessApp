package com.example.crinaed.database.entity;

import androidx.room.Entity;

import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

@Entity(primaryKeys = {"idUser", "idExercise"})
public class ExerciseInProgress  implements MyEntity{
    public long idUser;
    public long idExercise;
    public double progression;
    public int numStep;
    public long lastEdit;

    public ExerciseInProgress(long idUser, long idExercise, double progression, int numStep, long lastEdit) {
        this.idUser = idUser;
        this.idExercise = idExercise;
        this.progression = progression;
        this.numStep = numStep;
        this.lastEdit = lastEdit;
    }
    public ExerciseInProgress(JSONObject obj) throws JSONException {
        this.idUser = obj.getLong("idUser");
        this.idExercise = obj.getLong("idExercise");
        this.progression = obj.getDouble("progression");
        this.numStep = obj.getInt("numStep");
        this.lastEdit = Util.isoFormatToTimestamp(obj.getString("lastEdit"));
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idUser", idUser);
        obj.put("idExercise", idExercise);
        obj.put("progression", progression);
        obj.put("numStep", numStep);
        obj.put("lastEdit", Util.timestampToIso(lastEdit));
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseInProgress that = (ExerciseInProgress) o;
        return idUser == that.idUser &&
                idExercise == that.idExercise &&
                Double.compare(that.progression, progression) == 0 &&
                numStep == that.numStep &&
                lastEdit == that.lastEdit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idExercise, progression, numStep, lastEdit);
    }
}
