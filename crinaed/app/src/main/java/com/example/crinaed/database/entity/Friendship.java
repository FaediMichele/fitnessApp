package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.NO_ACTION;

@Entity(foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "idUser",
        childColumns = "idUser1",
        onDelete = NO_ACTION),
        @ForeignKey(entity = Friendship.class,
                parentColumns = "idUser",
                childColumns = "idUser2",
                onDelete = NO_ACTION)})
public class Friendship {
    @PrimaryKey
    public int idFriendShip;
    public int idUser1;
    public int idUser2;
}
