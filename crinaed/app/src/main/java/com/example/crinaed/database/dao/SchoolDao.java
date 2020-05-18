package com.example.crinaed.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.join.SchoolData;
import com.example.crinaed.database.entity.join.SchoolWithUser;

import java.util.List;

@Dao
public interface SchoolDao {
    @Transaction
    @Query("SELECT * FROM School")
    List<SchoolData> getData();

    @Transaction
    @Query("SELECT * FROM School")
    List<SchoolWithUser> getSchoolWithUser();

    @Insert
    void insertAll(School... schools);
}
