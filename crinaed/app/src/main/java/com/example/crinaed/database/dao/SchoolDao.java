package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.join.SchoolData;

import java.util.List;

@Dao
public interface SchoolDao {
    @Transaction
    @Query("SELECT * FROM School")
    LiveData<List<SchoolData>> get();

    @Transaction
    @Query("SELECT * FROM School WHERE idTrainer = (:idTrainer)")
    LiveData<List<SchoolData>> getSchoolTrained(long idTrainer);

    @Insert
    Long[] insert(School... schools);

    @Insert
    Long[] insert(Course... courses);

    @Update
    void update(School... schools);

    @Update
    void update(Course... courses);

    @Delete
    void delete(School... schools);

    @Delete
    void delete(Course... courses);
}
