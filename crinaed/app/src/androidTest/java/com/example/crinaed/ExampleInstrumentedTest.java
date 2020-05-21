package com.example.crinaed;

import android.app.Application;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.DatabaseUtil;
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
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.crinaed", appContext.getPackageName());
    }
}
