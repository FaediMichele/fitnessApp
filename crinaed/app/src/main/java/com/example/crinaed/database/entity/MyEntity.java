package com.example.crinaed.database.entity;

import org.json.JSONException;
import org.json.JSONObject;

public interface MyEntity {
    JSONObject toJson() throws JSONException;
}
