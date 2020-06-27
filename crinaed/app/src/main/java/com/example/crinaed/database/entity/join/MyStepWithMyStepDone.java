package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.MyStepDone;

import java.util.List;
import java.util.Objects;

public class MyStepWithMyStepDone {
    @Embedded public MyStep myStep;

    @Relation(parentColumn = "idMyStep", entityColumn = "idMyStep")
    public List<MyStepDone> stepDoneList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyStepWithMyStepDone that = (MyStepWithMyStepDone) o;
        return Objects.equals(myStep, that.myStep) &&
                Objects.equals(stepDoneList, that.stepDoneList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myStep, stepDoneList);
    }
}
