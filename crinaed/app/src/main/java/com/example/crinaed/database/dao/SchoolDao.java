package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.join.CourseSearchData;
import com.example.crinaed.database.entity.join.CourseWithExercise;
import com.example.crinaed.database.entity.join.SchoolData;

import java.util.List;

@Dao
public interface SchoolDao {
    @Transaction
    @Query("SELECT * FROM School")
    LiveData<List<SchoolData>> get();

    @Transaction
    @Query("SELECT * FROM School")
    List<School> getSchoolList();

    @Transaction
    @Query("SELECT * FROM Course WHERE isBought=1")
    List<Course> getCourseList();

    @Transaction
    @Query("SELECT * FROM Course WHERE isBought=1")
    LiveData<List<CourseWithExercise>> getCourseWithExercise();

    @Transaction
    @Query("SELECT * FROM Course WHERE idCourse IN (:idCourses)")
    LiveData<List<CourseSearchData>> getSearchedCourse(long[] idCourses);

    @Transaction
    @Query("SELECT * FROM Course WHERE idCourse=(:id)")
    LiveData<CourseWithExercise> getCourseWithExerciseById(long id);

    @Transaction
    @Query("SELECT * FROM School WHERE idSchool = (:idSchool)")
    LiveData<List<SchoolData>> getSchoolById(long idSchool);

    @Query("DELETE FROM Course WHERE isBought=0")
    void deleteCoursesSearched();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long[] insert(School... schools);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
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
