package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static androidx.room.ForeignKey.NO_ACTION;

@Entity(foreignKeys =
        {@ForeignKey(entity = User.class,
            parentColumns = "idUser",
            childColumns = "idUser1",
            onDelete = NO_ACTION),
        @ForeignKey(entity = User.class,
                parentColumns = "idUser",
                childColumns = "idUser2",
                onDelete = NO_ACTION)},
        indices = {@Index("idUser1"), @Index("idUser2")})
public class Friendship  implements MyEntity{
    @PrimaryKey(autoGenerate = true)
    public long idFriendship;
    public long idUser1;
    public long idUser2;
    public long blocked;

    public Friendship(long idFriendship, long idUser1, long idUser2) {
        this.idFriendship = idFriendship;
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
    }
    public Friendship(JSONObject obj) throws JSONException {
        this.idFriendship = obj.getLong("idFriendship");
        this.idUser1 = obj.getLong("idUser1");
        this.idUser2 = obj.getLong("idUser2");
        this.blocked= obj.getLong("blocked");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("idFriendship", idFriendship);
        obj.put("idUser1", idUser1);
        obj.put("idUser2", idUser2);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return idFriendship == that.idFriendship &&
                idUser1 == that.idUser1 &&
                idUser2 == that.idUser2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFriendship, idUser1, idUser2);
    }
}
