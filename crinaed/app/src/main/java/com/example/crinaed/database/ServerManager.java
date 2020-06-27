package com.example.crinaed.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.crinaed.R;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Single;
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

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                String strUTF8 = null;
                strUTF8 = new String(response.data, StandardCharsets.UTF_8);
                return Response.success(strUTF8,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        Volley.newRequestQueue(this.context).add(stringRequest);
    }


    public Future<String> login(String username, String password) throws JSONException {
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
        final Single<String> result = new Single<>();
        Callable<String> r = new Callable<String>() {
            @Override
            public String call() {
                try {
                    s.acquire();
                    Log.d("naed", "confirmed");
                    return result.getVal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return "";
                }
            }
        };
        managePost(param, new Lambda() {
            @Override
            public Object[] run(final Object... paramether) {
                Log.d("naed", "thread launched");
                AppDatabase.databaseWriteExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("naed", "login thread launched confirmed");
                        try {
                            DatabaseUtil.getInstance().getRepositoryManager().loadNewData(AppDatabase.getDatabase(context), paramether[0].toString());
                            JSONObject obj = new JSONObject(paramether[0].toString());

                            // Save in the sharedPreferences the sessionid
                            SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.sessionId), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor= preferences.edit();
                            editor.putString("value", obj.getString("SessionId"));
                            editor.apply();

                            result.setVal(obj.getString("SessionId"));
                            Log.d("sessionID", obj.getString("SessionId"));
                        } catch (JSONException | ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                            result.setVal("");
                            Log.d("sessionID", "here1");
                        }
                        s.release();
                        Log.d("naed", "login released");
                    }
                });
                return null;
            }
        }, new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                result.setVal("");
                Log.d("sessionID", "here2");
                s.release();
                return null;
            }
        });
        return AppDatabase.databaseWriteExecutor.submit(r);
    }
    public Future<Boolean> logout(String idSession) throws JSONException{
        final Semaphore s = new Semaphore(0);

        JSONObject body = new JSONObject();
        body.put("to", "logout");
        body.put("idSession", idSession);
        body.put("data", DatabaseUtil.getInstance().getRepositoryManager().getData());
        final Single<Boolean> result = new Single<>();

        managePost(body, new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                result.setVal(true);
                context.deleteDatabase(AppDatabase.DATABASE_NAME);
                s.release();
                return new Object[0];
            }
        }, new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                result.setVal(false);
                s.release();
                return new Object[0];
            }
        });

        Callable<Boolean> r = new Callable<Boolean>() {
            @Override
            public Boolean call() {
                try {
                    s.acquire();
                    return result.getVal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        };
        return AppDatabase.databaseWriteExecutor.submit(r);
    }


}
