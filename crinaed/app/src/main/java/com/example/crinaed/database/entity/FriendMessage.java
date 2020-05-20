package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.NO_ACTION;

@Entity(primaryKeys = {"friendShip", "date"}, foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "idUser", childColumns = "idReceiver", onDelete = CASCADE),
        @ForeignKey(entity = User.class, parentColumns = "idUser", childColumns = "idSender", onDelete = CASCADE),
        @ForeignKey(entity = Friendship.class, parentColumns = "idFriendship", childColumns = "idFriendship", onDelete = CASCADE)
})
public class FriendMessage {
    public long idFriendship;
    public Date date;
    public long idSender;
    public long idReceiver;
    public String message;
}
