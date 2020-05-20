package com.example.crinaed.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.FriendMessageDao;
import com.example.crinaed.database.dao.FriendshipDao;
import com.example.crinaed.database.entity.Exercise;
import com.example.crinaed.database.entity.FriendMessage;
import com.example.crinaed.database.entity.Friendship;
import com.example.crinaed.database.entity.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    public void addFriend(long idUser1, long idUser2){
        final Friendship friendship = new Friendship();
        friendship.idUser1 = idUser1;
        friendship.idUser2 = idUser2;
        friendship.idFriendship = 0;
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                friendshipDao.insert(friendship);
            }
        });
    }

    public void removeFriend(final Friendship friendship){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                friendshipDao.delete(friendship);
            }
        });
    }

    public void addMessage(final FriendMessage... friendMessages){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                friendMessageDao.insert(friendMessages);
            }
        });
    }

    public void updateMessage(final FriendMessage friendMessage){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                friendMessageDao.update(friendMessage);
            }
        });
    }
    public void deleteMessages(final FriendMessage... friendMessages){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                friendMessageDao.delete(friendMessages);
            }
        });
    }

    @Override
    public void loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("Friendship");
        final List<Friendship> friendships = new ArrayList<>();
        final List<FriendMessage> messages = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            Friendship friendship = new Friendship();
            friendship.idFriendship = obj.getLong("idFriendship");
            friendship.idUser1 = obj.getLong("idUser1");
            friendship.idUser2 = obj.getLong("idUser2");
            friendships.add(friendship);
        }
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            FriendMessage message = new FriendMessage();
            message.idFriendship = obj.getLong("idFriendship");
            message.idSender = obj.getLong("idSender");
            message.idReceiver = obj.getLong("idReceiver");
            message.date = new Date(obj.getLong("date"));
            message.message = obj.getString("message");
            messages.add(message);
        }
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                friendMessageDao.insert((FriendMessage[]) messages.toArray());
                friendshipDao.insert((Friendship[]) friendships.toArray());
            }
        });
    }
}

