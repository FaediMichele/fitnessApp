package com.example.crinaed.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.crinaed.database.repository.Repository;
import com.example.crinaed.database.repository.RepositoryManager;
import com.example.crinaed.util.Lambda;

import org.json.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DatabaseUtil {
    private static DatabaseUtil instance;
    private RepositoryManager repositoryManager;

    public static DatabaseUtil getInstance(){
        if(instance == null){
            synchronized (DatabaseUtil.class){
                if(instance == null){
                    instance = new DatabaseUtil();
                }
            }
        }
        return instance;
    }

    public void setApplication(Context context){
        repositoryManager = new RepositoryManager(context);
    }

    public RepositoryManager getRepositoryManager() {
        return repositoryManager;
    }
}
