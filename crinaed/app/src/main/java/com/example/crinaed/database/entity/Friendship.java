package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.NO_ACTION;

@Entity(foreignKeys =
        {@ForeignKey(entity = User.class,
            parentColumns = "idUser",
            childColumns = "idUser1",
            onDelete = NO_ACTION),
        @ForeignKey(entity = User.class,
                parentColumns = "idUser",
                childColumns = "idUser2",
                onDelete = NO_ACTION)})
public class Friendship {
    @PrimaryKey(autoGenerate = true)
    public long idFriendship;
    public long idUser1;
    public long idUser2;

    public Friendship(long idFriendship, long idUser1, long idUser2) {
        this.idFriendship = idFriendship;
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
    }
}
