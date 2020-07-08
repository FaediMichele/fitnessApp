package com.example.crinaed.database.entity.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.History;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.Review;
import com.example.crinaed.database.entity.School;

import java.util.List;
import java.util.Objects;

public class CommitmentWithMyStep {
    @Embedded public MyCommitment commitment;
    @Relation(entity = MyStep.class, parentColumn = "idCommitment", entityColumn = "idCommitment")
    public List<MyStepWithMyStepDone> steps;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommitmentWithMyStep that = (CommitmentWithMyStep) o;
        return Objects.equals(commitment, that.commitment) &&
                Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commitment, steps);
    }
}
