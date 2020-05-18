package com.example.crinaed.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.crinaed.database.entity.UserLevel;

import java.util.List;

@Dao
public interface UserLevelDao {
    @Query("SELECT * FROM UserLevel WHERE idUser IN (:idUser)")
    List<UserLevel> getUserLevels(int idUser);

    @Query("UPDATE UserLevel SET PE= (:nPE), level=(:nLv) WHERE idUser = (:idUser) AND cat = (:cat)")
    void updateLevel(int idUser, String cat, int nPE, int nLv);

    @Query("UPDATE UserLevel SET PE= (:nPE) WHERE idUser = (:idUser) AND cat = (:cat)")
    void updatePE(int idUser, String cat, int nPE);


    @Query("INSERT INTO UserLevel(idUser, cat, PE, level) VALUES ((:idUser), (:cat), 0, 1)")
    void insert(int idUser, String cat);
}
