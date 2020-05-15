package com.example.crinaed.database.entity.join;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.crinaed.database.entity.User;

import java.util.Date;

import static androidx.room.ForeignKey.NO_ACTION;

@Entity(tableName = "Message", primaryKeys = {"idReceiver", "idSender", "date"}, foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "idUser", childColumns = "idReceiver", onDelete = NO_ACTION),
        @ForeignKey(entity = User.class, parentColumns = "idUser", childColumns = "idSender", onDelete = NO_ACTION)
})
public class FriendMessage {
    public int idReceiver;
    public int idSender;
    public Date date;
    public String message;
}