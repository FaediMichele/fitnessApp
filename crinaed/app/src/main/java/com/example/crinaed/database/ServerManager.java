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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

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


    public void managePost(final JSONObject body, final Lambda onResponseMethod, final Lambda onErrorMethod){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ServerManager", "*" + response+ "*");
                if(onResponseMethod != null) {
                    onResponseMethod.run(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ServerManager", "*" + error.getMessage() + "*");
                if(onErrorMethod != null) {
                    onErrorMethod.run(error);
                }
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


    public Future<?> login(String username, String password) throws JSONException {
        final JSONObject data = new JSONObject();
        final JSONObject param = new JSONObject();
        final Context context= this.context;
        password = Util.hash(password);
        data.put("email", username);
        data.put("hashPassword", password);

        param.put("to", "login");
        param.put("data", data);

        // used to wait for the login or interrupt the operation if the server is not available
        final Semaphore s = new Semaphore(0);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    s.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        managePost(param, new Lambda() {
            @Override
            public Object[] run(final Object... paramether) {
                AppDatabase.databaseWriteExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DatabaseUtil.getInstance().getRepositoryManager().loadNewData(AppDatabase.getDatabase(context), paramether[0].toString());
                        } catch (JSONException | ExecutionException | InterruptedException ignore) {
                        }
                        s.release();
                    }
                });
                return null;
            }
        }, new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                s.release();
                return null;
            }
        });
        return AppDatabase.databaseWriteExecutor.submit(r);
    }
}
