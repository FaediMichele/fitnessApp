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

public class CommitmentWithMyStep {
    @Embedded public MyCommitment commitment;
    @Relation(parentColumn = "idCommitment", entityColumn = "idCommitment")
    public List<MyStep> steps;

    public static class HistoryWithExercise {
        @Embedded
        History history;

        @Relation(parentColumn = "idExercise", entityColumn = "idExercise")
        public List<Exercise> exercises;
    }

    public static class SchoolWithReview {
        @Embedded
        public School school;

        @Relation(parentColumn = "idSchool", entityColumn = "idSchool")
        public List<Review> reviews;
    }
}
