package com.example.crinaed;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.join.CommitmentWithMyStep;
import com.example.crinaed.database.repository.CommitmentRepository;
import com.example.crinaed.database.repository.CourseBoughtRepository;
import com.example.crinaed.database.repository.ExerciseAndStepRepository;
import com.example.crinaed.database.repository.ExerciseInProgressRepository;
import com.example.crinaed.database.repository.FriendRepository;
import com.example.crinaed.database.repository.HistoryRepository;
import com.example.crinaed.database.repository.ReviewRepository;
import com.example.crinaed.database.repository.SchoolRepository;
import com.example.crinaed.database.repository.UserRepository;

import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DatabaseTest {
    private Application application = ApplicationProvider.getApplicationContext();

    // Used to run the test for the LiveData that is particular "underbred"
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private UserRepository userRepository = new UserRepository(application);
    private SchoolRepository schoolRepository = new SchoolRepository(application);
    private CommitmentRepository commitmentRepository = new CommitmentRepository(application);
    private CourseBoughtRepository courseBoughtRepository = new CourseBoughtRepository(application);
    private ExerciseAndStepRepository exerciseAndStepRepository = new ExerciseAndStepRepository(application);
    private ExerciseInProgressRepository exerciseInProgressRepository = new ExerciseInProgressRepository(application);
    private FriendRepository friendRepository = new FriendRepository(application);
    private HistoryRepository historyRepository = new HistoryRepository(application);
    private ReviewRepository reviewRepository = new ReviewRepository(application);

    @Test
    public void testLoadJson(){
        String json = "{\n\tUser: [{idUser: \"123\", firstname: \"michele\", surname: \"faedi\", email: \"michele.faedi@studio.unibo.it\", hashPassword: \"hashPaswd\"}, {idUser: \"321\", firstname: \"cristian\", surname: \"casadei\", email: \"cristian.casadei6@studio.unibo.it\", hashPassword: \"hashPaswd1\"}],\n\tFriendship: [{idFriendship: \"1\", idUser1: \"123\", idUser2: \"321\"}],\n\tMessage: [{idFriendship: \"1\", date: \"2020-05-02T18:25:43Z\", message: \"Ciao questo è un messaggio\", idSender: \"123\", idReceiver: \"321\"}],\n\tLevel: [{idUser: \"123\", cat: \"sport\", PE: \"12300\", level: \"100\"}],\n\tHistory: [{idUser: \"123\", date: \"2020-05-01T18:25:43Z\", idExercise: \"90\"}],\n\tSchool: [{idSchool: \"1\", name: \"palestra giubilopoli\", email: \"giubilopolyJym@gmail.com\", address: \"via duefoglie 3\", idTrainer: \"321\"}],\n\tReview: [{idSchool: \"1\", idUser: \"123\", val: \"4\", comment: \"la palestra è piccola\"}],\n\tCourse: [{idCourse: \"420\", idSchool: \"1\", cat: \"sport\", name: \"Corso per principianti\", desc: \"Corso per le persone che intendono iniziare ad allenarsi pur non avendo esperienza\", minimumLevel: \"1\"}],\n\tExercise: [{idExercise: \"90\", idCourse: \"420\", level: \"1\", PE: \"120\", duration: \"10\", name: \"streching\", desc: \"Esercizio pensato per chi non è sciolto e vuole diventare più agile\", video: \"na\"}],\n\tStep: [{idExercise: \"90\", num: \"1\", name: \"riscaldamento\", desc: \"corsa di 1 km\", incVal: \"100\", unitMeasure: \"metri\", max: \"1000\", video: \"na\"}],\n\tExerciseInProgress: [{idUser: \"123\", idExercise: \"90\", numStep: \"1\", progression: \"400\", lastEdit: \"2020-05-02T20:25:43Z\"}],\n\tCourseBought: [{idUser: \"123\", idCourse: \"420\", level: \"1\", purchaseDate: \"2020-05-01T18:25:43Z\"}],\n\tMyCommitment: [{idCommitment: \"73\", name: \"studiare mobile\", desc: \"Fare il progetto in android\", duration: \"6000\", idUser: \"123\"}],\n\tMyStep: [{idCommitment: \"73\", num: \"1\", name: \"fare database sql\", incVal: \"60\", unitMeasure: \"minuti\", max: \"300\", progression: \"120\"}, {idCommitment: \"73\", num: \"2\", name: \"fare database nosql\", incVal: \"60\", unitMeasure: \"minuti\", max: \"300\", progression: \"0\"}]\n}";
        AppDatabase db = AppDatabase.getDatabase(application);
        DatabaseUtil dbUtil = DatabaseUtil.getInstance();

        dbUtil.addRepository(userRepository);
        dbUtil.addRepository(schoolRepository);
        dbUtil.addRepository(commitmentRepository);
        dbUtil.addRepository(courseBoughtRepository);
        dbUtil.addRepository(exerciseAndStepRepository);
        dbUtil.addRepository(exerciseInProgressRepository);
        dbUtil.addRepository(friendRepository);
        dbUtil.addRepository(historyRepository);
        dbUtil.addRepository(reviewRepository);
        try {
            dbUtil.loadNewData(db, json);
            assertTrue(true);
            Log.d("DatabaseTest", "Database loaded");
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.d("DatabaseTest", "" + e.getMessage());
            fail();
        }
    }

    // there is no need for deep test
    @Test
    public void testCorrectLoadJsonCommitment(){
        commitmentRepository.getCommitmentWithSteps().observeForever(new Observer<List<CommitmentWithMyStep>>() {
            @Override
            public void onChanged(List<CommitmentWithMyStep> commits) {
                assertNotNull(commits);
                assertEquals(commits.size(), 1);
                CommitmentWithMyStep commitment = commits.get(0);
                assertEquals(commitment.commitment.idCommitment, 73);
                assertEquals(commitment.steps.size(), 2);
            }
        });
    }

    @AfterClass
    public static void clearDatabase(){
        ApplicationProvider.getApplicationContext().getApplicationContext().deleteDatabase(AppDatabase.DATABASE_NAME);
        Log.d("DatabaseTest", "database formatted");
    }
}
