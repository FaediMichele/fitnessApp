package com.example.crinaed.database.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.FriendMessageDao;
import com.example.crinaed.database.dao.FriendshipDao;
import com.example.crinaed.database.entity.FriendMessage;
import com.example.crinaed.database.entity.Friendship;
import com.example.crinaed.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class FriendRepository implements Repository{

    private FriendshipDao friendshipDao;
    private FriendMessageDao friendMessageDao;

    public FriendRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        friendshipDao = db.friendshipDao();
        friendMessageDao = db.friendMessageDao();
    }

    public LiveData<List<Friendship>> getFriendship(long idUser){
        return friendshipDao.getFriendshipByIdUser(idUser);
    }

    public LiveData<List<FriendMessage>> getMessageByIdFriendship(long Friendship){
        return friendMessageDao.getMessageByIdFriendship(Friendship);
    }


    public Future<?> addFriend(long idUser1, long idUser2){
        final Friendship friendship = new Friendship(0, idUser1, idUser2);
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Long>() {
            @Override
            public Long call() {
                return friendshipDao.insert(friendship)[0];
            }
        });
    }

    public Future<?> removeFriend(final Friendship friendship){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                friendshipDao.delete(friendship);
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
        JSONArray array = data.getJSONArray("Friendship");
        final List<Friendship> friendships = new ArrayList<>();
        final List<FriendMessage> messages = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            Friendship friendship = new Friendship(obj.getLong("idFriendship"), obj.getLong("idUser1"), obj.getLong("idUser2"));
            friendships.add(friendship);
        }
        array = data.getJSONArray("Message");
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            FriendMessage message = new FriendMessage(obj.getLong("idFriendship"), Util.isoFormatToTimestamp(obj.getString("date")),
                    obj.getLong("idSender"), obj.getLong("idReceiver"), obj.getString("message"));
            messages.add(message);
        }
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                friendshipDao.insert(friendships.toArray(new Friendship[0]));
                friendMessageDao.insert(messages.toArray(new FriendMessage[0]));
            }
        });
    }
}

