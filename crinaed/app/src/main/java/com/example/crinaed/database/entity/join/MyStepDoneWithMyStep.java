package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.MyStepDone;

import java.util.Objects;

public class MyStepDoneWithMyStep {
    @Embedded
    public MyStepDone stepDone;

    @Relation(entityColumn = "idMyStep", parentColumn = "idMyStep")
    public MyStep step;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyStepDoneWithMyStep that = (MyStepDoneWithMyStep) o;
        return Objects.equals(stepDone, that.stepDone) &&
                Objects.equals(step, that.step);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepDone, step);
    }
}
