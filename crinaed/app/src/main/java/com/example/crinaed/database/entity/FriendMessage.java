package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.NO_ACTION;

@Entity(primaryKeys = {"idFriendship", "date"}, foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "idUser", childColumns = "idReceiver", onDelete = CASCADE),
        @ForeignKey(entity = User.class, parentColumns = "idUser", childColumns = "idSender", onDelete = CASCADE),
        @ForeignKey(entity = Friendship.class, parentColumns = "idFriendship", childColumns = "idFriendship", onDelete = CASCADE)},
        indices = {@Index("idSender"), @Index("idReceiver")})
public class FriendMessage  implements MyEntity{
    public long idFriendship;
    public long date;
    public long idSender;
    public long idReceiver;
    public String message;

    public FriendMessage(long idFriendship, long date, long idSender, long idReceiver, String message) {
        this.idFriendship = idFriendship;
        this.date = date;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.message = message;
    }
    public FriendMessage(JSONObject obj) throws JSONException {
        this.idFriendship = obj.getLong("idFriendship");
        this.date = Util.isoFormatToTimestamp(obj.getString("date"));
        this.idSender = obj.getLong("idSender");
        this.idReceiver = obj.getLong("idReceiver");
        this.message = obj.getString("message");
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idFriendship", idFriendship);
        obj.put("date", date);
        obj.put("idSender", idSender);
        obj.put("idReceiver", idReceiver);
        obj.put("message", message);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendMessage that = (FriendMessage) o;
        return idFriendship == that.idFriendship &&
                date == that.date &&
                idSender == that.idSender &&
                idReceiver == that.idReceiver &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFriendship, date, idSender, idReceiver, message);
    }
}
