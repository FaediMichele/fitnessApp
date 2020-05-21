package com.example.crinaed.database.repository;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Future;

public interface Repository {

    Future<?> loadData(JSONObject data) throws JSONException;
}
