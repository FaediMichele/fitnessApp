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
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.MyStepDone;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.database.entity.join.CourseBoughtWithCourse;
import com.example.crinaed.database.repository.RepositoryManager;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;

import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
    public void testLoadJson(final String json){
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

    }

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
        addCommitment();
        checkExtract();

    }

    // there is no need for deep test
    private void testCorrectLoadJsonCommitment(){
        repositoryManager.getCommitmentRepository().getCommitmentWithSteps().observeForever(new Observer<List<CommitmentWithMyStep>>() {
            @Override
            public void onChanged(List<CommitmentWithMyStep> commits) {
                assertNotNull(commits);
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

    private void testCorrectLoadJsonCourseBoughtRepository(){
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


    private void testCommitment(){
        final Integer[] status = new Integer[]{0};
        repositoryManager.getCommitmentRepository().getCommitmentWithSteps().observeForever(new Observer<List<CommitmentWithMyStep>>() {
            @Override
            public void onChanged(List<CommitmentWithMyStep> commits) {
                List<MyStep> steps = commits.get(0).steps;
                assertEquals(steps.size(), 1);
                steps = commits.get(1).steps;
                assertEquals(steps.size(), 2);
                try {
                   /*repositoryManager.getCommitmentRepository().getCommitmentWithSteps().observeForever(new Observer<List<CommitmentWithMyStep>>() {
                        @Override
                        public void onChanged(List<CommitmentWithMyStep> commitmentWithMySteps) {
                            Log.d("testCommitment", "out: "+commitmentWithMySteps.toString());
                        }
                    });*/
                   if(status[0] == 0){
                       assertEquals(repositoryManager.getCommitmentRepository().updateMyStepDone().get().size(), 2);
                       assertEquals(0, repositoryManager.getCommitmentRepository().updateMyStepDone().get().size());
                       status[0]++;
                   }

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    // also test the correct connection with the server
    private void addCommitment(){
        final MyCommitment mc = new MyCommitment(-1, "Dimagrire", "Dimagrire 10kg prima dell'estate", new Date().getTime(), repositoryManager.getIdUser());
        final MyStep[] steps = new MyStep[3];
        steps[0] = new MyStep(-1, -1, "Mangiare mele", "mele", 5, 1, "progression");
        steps[1] = new MyStep(-1, -1, "Corsa", "km", 2, 1, "progression");
        steps[2] = new MyStep(-1, -1, "Studiare", "ore", 12, 2, "progression");

        repositoryManager.getCommitmentRepository().insert(new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                assertTrue((boolean) paramether[0]);
                return new Object[0];
            }
        }, mc, steps);
    }

    private void checkExtract(){
        Log.d("database-out", repositoryManager.getData().toString());
        //testLoadJson(repositoryManager.getData().toString());
    }

    @AfterClass
    public static void clearDatabase(){
/*
        ApplicationProvider.getApplicationContext().getApplicationContext().deleteDatabase(AppDatabase.DATABASE_NAME);
        Log.d("DatabaseTest", "database formatted");*/
    }

    @BeforeClass
    public static void init(){
        DatabaseUtil.getInstance().setApplication(ApplicationProvider.getApplicationContext().getApplicationContext());
    }
}
