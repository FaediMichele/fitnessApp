package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.MyStepDone;

import java.util.List;

public class MyStepWithMyStepDone {
    @Embedded public MyStep myStep;

    @Relation(parentColumn = "idMyStep", entityColumn = "idMyStep")
    public List<MyStepDone> stepDoneList;
}
