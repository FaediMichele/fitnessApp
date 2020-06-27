package com.example.crinaed;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
import com.example.crinaed.database.entity.join.MyStepDoneWithMyStep;
import com.example.crinaed.database.repository.RepositoryManager;
import com.example.crinaed.util.Category;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Single;
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
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

    private String sessionId;

    @Test
    public void testServerLoad(){
        ApplicationProvider.getApplicationContext().getApplicationContext().deleteDatabase(AppDatabase.DATABASE_NAME);
        try{
            /* TODO add the session id in the shared preferences on the login */
            // check the connection with the server and the parse of the results.
            sessionId = ServerManager.getInstance(application).login("ciaobello", "p").get(3,TimeUnit.SECONDS);
            assertNotEquals("", sessionId);
            assertTrue(true);
        } catch (ExecutionException | TimeoutException | JSONException | InterruptedException e) {
            e.printStackTrace();
            fail(); // maybe sever offline
        }
        //addCommitment();
        changeStepDone();
        checkExtract();

    }


    private void addCommitment(){
        final MyCommitment mc = new MyCommitment(-1, "Studiare", "Studiare per la sessione di giugno", new Date().getTime(), repositoryManager.getIdUser());
        final MyStep[] steps = new MyStep[2];
        steps[0] = new MyStep(-1, -1, "Studiare mobile", "ore", 6, 1, "progression" , Category.MENTAL);
        steps[1] = new MyStep(-1, -1, "Studiare metodi numerici", "ore", 6, 1, "progression", Category.MENTAL);

        repositoryManager.getCommitmentRepository().insert(new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                assertTrue((boolean) paramether[0]);
                return new Object[0];
            }
        }, mc, steps);
    }

    private void changeStepDone(){
        final LiveData<List<MyStepDoneWithMyStep>> s = repositoryManager.getCommitmentRepository().getStepOnGoing();
        final Single<Boolean> b = new Single<>(true);
        s.observeForever(new Observer<List<MyStepDoneWithMyStep>>() {
            @Override
            public void onChanged(List<MyStepDoneWithMyStep> steps) {
                MyStepDoneWithMyStep step=steps.get(0);
                if(b.getVal()) {
                    step.stepDone.result = 100;
                    repositoryManager.getCommitmentRepository().updateMyStepDone(step.stepDone);
                    b.setVal(false);
                }
            }
        });

    }

    private void checkExtract(){
        Log.d("database-out", repositoryManager.getData().toString());
        try {
            assertTrue(ServerManager.getInstance(application).logout(sessionId).get(1, TimeUnit.SECONDS));
        } catch (JSONException | InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            fail();
        }
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
