package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverter;
import androidx.room.Update;

import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.MyStepDone;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.database.entity.join.MyStepWithMyStepDone;

import java.util.List;

@Dao
public interface MyCommitmentDao {
    @Insert
    Long[] insert(MyCommitment... commitments);

    @Insert
    Long[] insert(MyStep... mySteps);

    @Insert
    Long[] insert(MyStepDone... myStepsDone);

    @Update
    void update(MyCommitment... commitments);

    @Update
    void update(MyStep... steps);

    @Update
    void update(MyStepDone... myStepsDone);

    @Delete
    void delete(MyCommitment... commitments);

    @Delete
    void delete(MyStep... steps);

    @Transaction
    @Query("SELECT * FROM MyCommitment")
    LiveData<List<CommitmentWithMyStep>> getCommitmentWithMyStep();

    @Transaction
    @Query("SELECT * FROM MyCommitment")
    List<CommitmentWithMyStep> getCommitmentWithMyStepList();

    @Query("SELECT * FROM MyStep WHERE idCommitment = (:idCommitment)")
    List<MyStep> getStepByIdCommitment(long idCommitment);

    @Transaction
    @Query("SELECT * FROM MyStep")
    LiveData<List<MyStepWithMyStepDone>> getStepDone();

    @Query("SELECT * FROM MyStepDone WHERE idMyStep = (:idMyStep) ORDER BY dateStart DESC LIMIT 1")
    MyStepDone getLastStepDone(long idMyStep);



    @Transaction
    @Query("SELECT * FROM MyStepDone WHERE dateStart IN(SELECT MAX(dateStart) FROM MyStepDone GROUP BY idMyStep) AND idMyStep IN(SELECT idMyStep FROM MyStep WHERE category=(:category))")
    LiveData<List<MyStepDoneWithMyStep>> getLastMyStepDoneWithMyStep(int category);

    @Transaction
    @Query("SELECT * FROM MyStepDone WHERE dateStart IN(SELECT MAX(dateStart) FROM MyStepDone GROUP BY idMyStep) AND idMyStep IN(SELECT idMyStep FROM MyStep WHERE category=(:category))")
    List<MyStepDoneWithMyStep> getLastMyStepDoneWithMyStepList(int category);


    @Transaction
    @Query("SELECT * FROM MyStepDone WHERE idMyStep IN(SELECT idMyStep FROM MyStep WHERE category=(:category)) ORDER BY dateStart")
    List<MyStepDoneWithMyStep> getMyStepDoneWithMyStepWithCategory(int category);

    @Transaction
    @Query("SELECT * FROM MyStepDone WHERE idMyStep IN(SELECT idMyStep FROM MyStep WHERE category=(:category)) AND dateStart >= (:date) ORDER BY dateStart")
    List<MyStepDoneWithMyStep> getMyStepDoneWithMyStepWithCategoryAndDataList(int category, long date);

    @Transaction
    @Query("SELECT * FROM MyStepDone WHERE idMyStep IN(SELECT idMyStep FROM MyStep WHERE category=(:category) AND repetitionDay=(:repetitionDay)) AND dateStart >= (:dateStart)  AND dateStart < (:dateEnd)ORDER BY dateStart")
    List<MyStepDoneWithMyStep> getMyStepDoneWithMyStepWithCategoryAndDataList(int category, long dateStart, long dateEnd, int repetitionDay);

    @Transaction
    @Query("SELECT * FROM MyStepDone WHERE idMyStep IN(SELECT idMyStep FROM MyStep WHERE category=(:category) AND repetitionDay=(:repetitionDay)) AND dateStart >= (:date) ORDER BY dateStart")
    LiveData<List<MyStepDoneWithMyStep>> getMyStepDoneWithMyStepWithCategoryAndData(int category, long date, int repetitionDay);


    @Query("SELECT * FROM MyCommitment")
    List<MyCommitment> getCommitmentList();

    @Query("SELECT * FROM MyStep")
    List<MyStep> getMyStepList();

    @Query("SELECT * FROM MyStepDone")
    List<MyStepDone> getMyStepDoneList();
}
