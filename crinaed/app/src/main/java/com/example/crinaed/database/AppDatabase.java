package com.example.crinaed.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.crinaed.database.dao.ExerciseAndStepDao;
import com.example.crinaed.database.dao.FriendMessageDao;
import com.example.crinaed.database.dao.FriendshipDao;
import com.example.crinaed.database.dao.MyCommitmentDao;
import com.example.crinaed.database.dao.ReviewDao;
import com.example.crinaed.database.dao.SchoolDao;
import com.example.crinaed.database.dao.UserDao;
import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.FriendMessage;
import com.example.crinaed.database.entity.Friendship;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyMotivationalPhrase;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.database.entity.MyStepDone;
import com.example.crinaed.database.entity.Review;
import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserLevel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Course.class, Exercise.class, FriendMessage.class,
        Friendship.class,MyCommitment.class, MyStep.class, Review.class, School.class, User.class,
        UserLevel.class, MyStepDone.class, MyMotivationalPhrase.class}, version = 1, exportSchema = false)
abstract public class AppDatabase extends RoomDatabase {
    abstract public FriendMessageDao friendMessageDao();
    abstract public FriendshipDao friendshipDao();
    abstract public SchoolDao schoolDao();
    abstract public UserDao userDao();
    abstract public MyCommitmentDao commitmentDao();
    abstract public ExerciseAndStepDao exerciseAndStepDao();
    abstract public ReviewDao reviewDao();

    private static AppDatabase instance;
    private static final int NUMBER_OF_THREAD = 4;
    static public final String DATABASE_NAME = "database";

    static public final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);


    public static AppDatabase getDatabase(Context context) {
        if(instance == null){
            synchronized (AppDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
