package com.example.crinaed.database.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.FriendMessageDao;
import com.example.crinaed.database.dao.FriendshipDao;
import com.example.crinaed.database.entity.FriendMessage;
import com.example.crinaed.database.entity.Friendship;
import com.example.crinaed.database.entity.join.user.UserWithUser;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FriendRepository extends Repository{

    private FriendshipDao friendshipDao;
    private FriendMessageDao friendMessageDao;

    public FriendRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        friendshipDao = db.friendshipDao();
        friendMessageDao = db.friendMessageDao();
        setContext(context);
    }

    public LiveData<UserWithUser> getFriendshipByFriend(long idFriend){
        return friendshipDao.getFriendshipByFriend(idFriend);
    }

    public LiveData<List<FriendMessage>> getMessageByIdFriendship(long idFriendship){
        return friendMessageDao.getMessageByIdFriendship(idFriendship);
    }


    public Future<?> addFriend(final Friendship friendship, final Lambda onAddDone){
        Log.d("naed", "creating new friendship" + friendship.idFriendship);
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Long>() {
            @Override
            public Long call() {
                long ret= friendshipDao.insert(friendship)[0];
                onAddDone.run(ret);
                Log.d("naed", "newFriendship: " + ret);
                return ret;
            }
        });
    }

    public Future<?> removeFriend(final long friendship){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Log.d("naed", "deleting friendship: " + friendship);
                friendshipDao.deleteFriendship(friendship);
            }
        });
    }

    public Future<?> addMessage(final FriendMessage... friendMessages){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                friendMessageDao.insert(friendMessages);
            }
        });
    }

    public Future<?> updateMessage(final FriendMessage friendMessage){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                friendMessageDao.update(friendMessage);
            }
        });
    }
    public Future<?> deleteMessages(final FriendMessage... friendMessages){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                friendMessageDao.delete(friendMessages);
            }
        });
    }

    @Override
    public Future<?> loadData(JSONObject data) throws JSONException {
        final List<FriendMessage> messages = new ArrayList<>();
        final List<Friendship> friendships = new ArrayList<>();

        if(data.has("Friendship")) {
            try {
                final JSONArray arrayFriend = data.getJSONArray("Friendship");
                for (int i = 0; i < arrayFriend.length(); i++) {
                    friendships.add(new Friendship(arrayFriend.getJSONObject(i)));
                }
            } catch (JSONException ignore) {
            }
        }

        if(data.has("Message")) {
            try {
                final JSONArray arrayMessage = data.getJSONArray("Message");
                for (int i = 0; i < arrayMessage.length(); i++) {
                    messages.add(new FriendMessage(arrayMessage.getJSONObject(i)));
                }
            } catch (JSONException ignore) {
                ignore.printStackTrace();
            }
        }

        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                friendshipDao.insert(friendships.toArray(new Friendship[0]));
                friendMessageDao.insert(messages.toArray(new FriendMessage[0]));
            }
        });
    }

    @Override
    public Future<?> extractData(final JSONObject root) {
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    root.put("Friendship", listToJSONArray(friendshipDao.getFriendshipList()));
                    root.put("Message", listToJSONArray(friendMessageDao.getMessageList()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

