package com.example.crinaed;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.database.entity.CourseBought;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.database.entity.join.CourseBoughtWithCourse;
import com.example.crinaed.database.repository.RepositoryManager;
import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class DatabaseTest {
    private Application application = ApplicationProvider.getApplicationContext();
    private RepositoryManager repositoryManager;
    // Used to run the test for the LiveData that is particular "underbred"
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    public DatabaseTest(){
        repositoryManager = DatabaseUtil.getInstance().getRepositoryManager();
    }

    // Using the server.
    /*@Test
    public void testLoadJson(){
        final String json = "{\n\tUser: [{idUser: \"123\", firstname: \"michele\", surname: \"faedi\", email: \"michele.faedi@studio.unibo.it\", hashPassword: \"hashPaswd\"}, {idUser: \"321\", firstname: \"cristian\", surname: \"casadei\", email: \"cristian.casadei6@studio.unibo.it\", hashPassword: \"hashPaswd1\"}],\n\tFriendship: [{idFriendship: \"1\", idUser1: \"123\", idUser2: \"321\"}],\n\tMessage: [{idFriendship: \"1\", date: \"2020-05-02T18:25:43Z\", message: \"Ciao questo è un messaggio\", idSender: \"123\", idReceiver: \"321\"}],\n\tLevel: [{idUser: \"123\", cat: \"sport\", PE: \"12300\", level: \"100\"}],\n\tHistory: [{idUser: \"123\", date: \"2020-05-01T18:25:43Z\", idExercise: \"90\"}],\n\tSchool: [{idSchool: \"1\", name: \"palestra giubilopoli\", email: \"giubilopolyJym@gmail.com\", address: \"via duefoglie 3\", idTrainer: \"321\"}],\n\tReview: [{idSchool: \"1\", idUser: \"123\", val: \"4\", comment: \"la palestra è piccola\"}],\n\tCourse: [{idCourse: \"420\", idSchool: \"1\", cat: \"sport\", name: \"Corso per principianti\", desc: \"Corso per le persone che intendono iniziare ad allenarsi pur non avendo esperienza\", minimumLevel: \"1\"}],\n\tExercise: [{idExercise: \"90\", idCourse: \"420\", level: \"1\", PE: \"120\", duration: \"10\", name: \"streching\", desc: \"Esercizio pensato per chi non è sciolto e vuole diventare più agile\", video: \"na\"}],\n\tStep: [{idExercise: \"90\", num: \"1\", name: \"riscaldamento\", desc: \"corsa di 1 km\", incVal: \"100\", unitMeasure: \"metri\", max: \"1000\", video: \"na\"}],\n\tExerciseInProgress: [{idUser: \"123\", idExercise: \"90\", numStep: \"1\", progression: \"400\", lastEdit: \"2020-05-02T20:25:43Z\"}],\n\tCourseBought: [{idUser: \"123\", idCourse: \"420\", level: \"1\", purchaseDate: \"2020-05-01T18:25:43Z\"}],\n\tMyCommitment: [{idCommitment: \"73\", name: \"studiare mobile\", desc: \"Fare il progetto in android\", creationDate: \"2020-05-02T20:25:43Z\", idUser: \"123\"}],\n\tMyStep: [{idMyStep: \"42\", idCommitment: \"73\", name: \"fare database sql\", unitMeasure: \"minuti\", max: \"300\", repetitionDay: \"1\", type: \"incremental\"}],\n\tMyStepDone: [{idMyStep: \"42\", dateStart: \"2020-05-03T20:25:43Z\", result: \"300\"}]\n}";


        AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getDatabase(application);
                try {
                    repositoryManager.loadNewData(db, json);
                } catch (JSONException | ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("DatabaseTest", "Database loaded");
            }
        });

    }*/

    @Test
    public void testServerLoad(){
        ApplicationProvider.getApplicationContext().getApplicationContext().deleteDatabase(AppDatabase.DATABASE_NAME);
        try{
            ServerManager.getInstance(application).login("ciaobello", "p");
            AppDatabase.databaseWriteExecutor.awaitTermination(1, TimeUnit.SECONDS);
            assertTrue(true);
        } catch (JSONException | InterruptedException e) {
            e.printStackTrace();
            fail();
        }
        testCorrectLoadJsonCommitment();
        testCorrectLoadJsonCourseBoughtRepository();
        testCommitment();
    }

    // there is no need for deep test
    public void testCorrectLoadJsonCommitment(){
        repositoryManager.getCommitmentRepository().getCommitmentWithSteps().observeForever(new Observer<List<CommitmentWithMyStep>>() {
            @Override
            public void onChanged(List<CommitmentWithMyStep> commits) {
                assertNotNull(commits);
                assertEquals(commits.size(), 2);
                CommitmentWithMyStep commitment = commits.get(0);
                assertEquals(commitment.commitment.idCommitment, 73);
                assertEquals(commitment.commitment.name, "studiare mobile");
                assertEquals(commitment.commitment.desc, "Fare il progetto in android");
                assertEquals(commitment.commitment.creationDate, Util.isoFormatToTimestamp("2020-05-02T20:25:43Z"));
                assertEquals(commitment.commitment.idUser, 123);
                assertEquals(commitment.steps.size(), 1);
                final MyStep st1 = commitment.steps.get(0);
                assertNotNull(st1);
                assertEquals(st1.idCommitment, commitment.commitment.idCommitment);
                assertEquals(st1.name, "fare database sql");
                assertEquals(st1.unitMeasure, "minuti");
                assertEquals(300, st1.max, 0.0);
            }
        });
    }

    public void testCorrectLoadJsonCourseBoughtRepository(){
        repositoryManager.getCourseBoughtRepository().getCourses().observeForever(new Observer<List<CourseBoughtWithCourse>>() {
            @Override
            public void onChanged(List<CourseBoughtWithCourse> courses) {
                assertNotNull(courses);
                assertEquals(courses.size(), 2);
                CourseBought us = courses.get(0).courseBought;
                assertNotNull(us);
                assertEquals(us.idUser, 123);
                assertEquals(us.idCourse, 420);
                assertEquals(us.level, 1);
                assertEquals(us.purchaseDate, Util.isoFormatToTimestamp("2020-05-01T18:25:43Z"));
            }
        });
    }


    public void testCommitment(){
        repositoryManager.getCommitmentRepository().getCommitmentWithSteps().observeForever(new Observer<List<CommitmentWithMyStep>>() {
            @Override
            public void onChanged(List<CommitmentWithMyStep> commits) {
                assertEquals(commits.size(), 2);
                List<MyStep> steps = commits.get(0).steps;
                assertEquals(steps.size(), 1);
                steps = commits.get(1).steps;
                assertEquals(steps.size(), 2);
                try {
                    Log.d("testCommitment", "out: "+ repositoryManager.getCommitmentRepository().getCommitmentWithSteps().toString());
                    assertEquals(repositoryManager.getCommitmentRepository().updateMyStepDone().get().size(), 2);
                    assertEquals(0, repositoryManager.getCommitmentRepository().updateMyStepDone().get().size());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @AfterClass
    public static void clearDatabase(){
        ApplicationProvider.getApplicationContext().getApplicationContext().deleteDatabase(AppDatabase.DATABASE_NAME);
        Log.d("DatabaseTest", "database formatted");
    }

    @BeforeClass
    public static void init(){
        DatabaseUtil.getInstance().setApplication(ApplicationProvider.getApplicationContext().getApplicationContext());
    }
}
