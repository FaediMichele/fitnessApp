package com.example.crinaed.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.MyStepDone;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.database.entity.join.MyCommitmentWithMyMotivationalPhrase;
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
    @Query("SELECT * FROM MyCommitment WHERE idCommitment IN (SELECT idCommitment FROM MyStep WHERE category=(:category)) AND ended=0")
    LiveData<List<CommitmentWithMyStep>> getCommitmentWithMyStep(int category);

    @Transaction
    @Query("SELECT * FROM MyCommitment WHERE idCommitment IN (SELECT idCommitment FROM MyStep WHERE category=(:category)) AND ended=1")
    LiveData<List<CommitmentWithMyStep>> getCommitmentEndedWithMyStep(int category);

    @Transaction
    @Query("SELECT * FROM MyCommitment WHERE ended=0")
    List<CommitmentWithMyStep> getCommitmentWithMyStepList();

    @Query("SELECT * FROM MyStep WHERE idCommitment = (:idCommitment)")
    List<MyStep> getStepByIdCommitment(long idCommitment);

    @Transaction
    @Query("SELECT * FROM MyStep")
    LiveData<List<MyStepWithMyStepDone>> getStepDone();

    @Query("SELECT * FROM MyStepDone WHERE idMyStep = (:idMyStep) ORDER BY dateStart DESC LIMIT 1")
    MyStepDone getLastStepDone(long idMyStep);



    @Transaction
    @Query("SELECT * FROM MyStepDone WHERE dateStart > (:afterDay) AND idMyStep IN(SELECT idMyStep FROM MyStep WHERE category=(:category) AND repetitionDay=(:repetition) AND idCommitment NOT IN (SELECT idCommitment FROM MyCommitment WHERE ended=1))")
    LiveData<List<MyStepDoneWithMyStep>> getLastMyStepDoneWithMyStep(int category, long afterDay, int repetition);

    @Transaction
    @Query("SELECT * FROM MyStepDone WHERE dateStart > (:afterDay) AND idMyStep IN(SELECT idMyStep FROM MyStep WHERE category=(:category) AND repetitionDay=(:repetition))")
    List<MyStepDoneWithMyStep> getLastMyStepDoneWithMyStepList(int category, long afterDay, int repetition);


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


    @Transaction
    @Query("SELECT * FROM MyStepDone WHERE idMyStep IN(SELECT idMyStep FROM MyStep WHERE category=(:category) AND repetitionDay=(:repetitionDay) AND idCommitment=(:idCommitment)) AND dateStart >= (:date) ORDER BY dateStart")
    LiveData<List<MyStepDoneWithMyStep>> getMyStepDoneWithMyStepWithCategoryAndData(long idCommitment, int category, long date, int repetitionDay);

    @Transaction
    @Query("SELECT * FROM MyCommitment")
    LiveData<List<MyCommitmentWithMyMotivationalPhrase>> getMotivationalPhrase();


    @Query("SELECT * FROM MyCommitment")
    List<MyCommitment> getCommitmentList();

    @Query("SELECT * FROM MyStep")
    List<MyStep> getMyStepList();

    @Query("SELECT * FROM MyStepDone")
    List<MyStepDone> getMyStepDoneList();

    @Query("SELECT * FROM MyCommitment WHERE name=(:name)")
    LiveData<List<MyCommitment>> getCommitmentWithName(String name);
}
