package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import org.json.JSONException;
import org.json.JSONObject;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"idCommitment", "percentage"}, foreignKeys = {
        @ForeignKey(entity = MyCommitment.class, parentColumns = "idCommitment", childColumns = "idCommitment", onDelete = CASCADE)
})
public class MyMotivationalPhrase implements  MyEntity{
    long idCommitment;
    int percentage;
    String phrase;

    public MyMotivationalPhrase(JSONObject obj) throws JSONException {
        this(obj.getLong("idCommitment"), obj.getInt("percentage"), obj.getString("phrase"));
    }
    public MyMotivationalPhrase(long idCommitment, int percentage, String phrase){
        this.idCommitment=idCommitment;
        this.percentage=percentage;
        this.phrase=phrase;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject ret = new JSONObject();
        ret.put("idCommitment", idCommitment);
        ret.put("percentage", percentage);
        ret.put("phrase", phrase);
        return ret;
    }
}
