package com.example.crinaed.database.repository;

import org.json.JSONException;
import org.json.JSONObject;

public interface Repository {

    void loadData(JSONObject data) throws JSONException;
}
