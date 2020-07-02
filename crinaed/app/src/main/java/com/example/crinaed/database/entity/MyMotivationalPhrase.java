package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import org.json.JSONException;
import org.json.JSONObject;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"idMyCommitment", "percentage"}, foreignKeys = {
        @ForeignKey(entity = MyCommitment.class, parentColumns = "idMyCommitment", childColumns = "idMyCommitment", onDelete = CASCADE)
})
public class MyMotivationalPhrase implements  MyEntity{
    long idMyCommitment;
    int percentage;
    String phrase;

    public MyMotivationalPhrase(JSONObject obj) throws JSONException {
        this(obj.getLong("idMyCommitment"), obj.getInt("percentage"), obj.getString("phrase"));
    }
    public MyMotivationalPhrase(long idMyCommitment, int percentage, String phrase){
        this.idMyCommitment=idMyCommitment;
        this.percentage=percentage;
        this.phrase=phrase;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject ret = new JSONObject();
        ret.put("idMyCommitment", idMyCommitment);
        ret.put("percentage", percentage);
        ret.put("phrase", phrase);
        return ret;
    }
}
