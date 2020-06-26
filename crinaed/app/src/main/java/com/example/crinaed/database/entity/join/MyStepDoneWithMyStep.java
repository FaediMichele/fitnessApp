package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.MyStepDone;

public class MyStepDoneWithMyStep {
    @Embedded
    public MyStepDone stepDone;

    @Relation(entityColumn = "idMyStep", parentColumn = "idMyStep")
    public MyStep step;
}
