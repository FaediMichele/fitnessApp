package com.example.crinaed.database;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ServerManager {
    private static final String SERVER = "http://192.168.1.111:8080/";
    private static ServerManager instance;
    private Context context;
    private ServerManager(Context context){
        this.context=context;
    }

    static public ServerManager getInstance(Context context){
        if(instance == null){
            synchronized (DatabaseUtil.class){
                if(instance == null){
                    instance = new ServerManager(context);
                }
            }
        }
        return instance;
    }


    public void ManagePost(final JSONObject body, final Lambda onResponseMethod, final Lambda onErrorMethod){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onResponseMethod.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onErrorMethod.run(error);
                Log.d("login", "error: *"+ error.toString() + "*");
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        Volley.newRequestQueue(this.context).add(stringRequest);
    }


    public void login(String username, String password) throws JSONException {
        final JSONObject data = new JSONObject();
        final JSONObject param = new JSONObject();
        final Context context= this.context;
        password = Util.hash(password);
        data.put("email", username);
        data.put("hashPassword", password);

        param.put("to", "login");
        param.put("data", data);
        ManagePost(param, new Lambda() {
            @Override
            public Object[] run(final Object... paramether) {
                AppDatabase.databaseWriteExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DatabaseUtil.getInstance().getRepositoryManager().loadNewData(AppDatabase.getDatabase(context), paramether[0].toString());
                        } catch (JSONException | ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return null;
            }
        }, new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                Log.d("login", "error"+ paramether[0].toString());
                return null;
            }
        });
    }
}
