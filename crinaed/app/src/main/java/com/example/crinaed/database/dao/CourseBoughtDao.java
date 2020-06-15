package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.crinaed.database.entity.CourseBought;
import com.example.crinaed.database.entity.join.CourseBoughtWithCourse;
import com.example.crinaed.database.entity.join.user.UserCourseBought;

import java.util.List;

@Dao
public interface CourseBoughtDao {

    @Insert
    Long[] insert(CourseBought... courseBoughts);

    @Update
    void update(CourseBought... courseBoughts);

    @Delete
    void delete(CourseBought... courseBoughts);

    @Transaction
    @Query("SELECT * FROM CourseBought")
    LiveData<List<CourseBoughtWithCourse>> getCourseBought();

    @Transaction
    @Query("SELECT * FROM CourseBought")
    List<CourseBoughtWithCourse> getCourseBoughtList();

    @Query("SELECT * FROM CourseBought")
    List<CourseBought> getCouseBoughtList();
}
