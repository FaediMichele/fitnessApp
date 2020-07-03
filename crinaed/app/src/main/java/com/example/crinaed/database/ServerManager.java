package com.example.crinaed.database;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.crinaed.R;
import com.example.crinaed.database.entity.FriendMessage;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ServerManager {
    private static final String SERVER = "http://192.168.1.111:8080";
    private static ServerManager instance;
    private Context context;
    private final Single<Boolean> pollingMessageOn = new Single<>(false);
    private final Single<Long> lastDate = new Single<>(0L);
    private final Single<Long> idFriendship = new Single<>(0L);
    private ServerManager(Context context){
        this.context=context;
    }

    public enum FileType{
        Commitment("idCommitment"), Course("idCourse"), Exercise("idExercise");
        private String key;
        FileType(String key){
            this.key=key;
        }

        public String getKey() {
            return key;
        }
    }

    static public ServerManager getInstance(Context context){
        if(instance == null) {
            synchronized (DatabaseUtil.class){
                if(instance == null){
                    instance = new ServerManager(context);
                }
            }
        }
        return instance;
    }



    public void manageGet(final String query, final Lambda onResponseMethod, final Lambda onErrorMethod){
        StringRequest request = new StringRequest(Request.Method.GET, SERVER + (query.equals("") ? "" : "?" + query), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onResponseMethod.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onErrorMethod.run(error);
            }
        });
        Volley.newRequestQueue(this.context).add(request);
    }

    private void managePost(final String body, final Lambda onResponseMethod, final Lambda onErrorMethod){
        managePost(body, onResponseMethod, onErrorMethod, "");
    }
    private void managePost(final String body, final Lambda onResponseMethod, final Lambda onErrorMethod, final String query){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER+(query.equals("")?"":"?"+query), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("ServerManager", "*" + response+ "*");
                if(onResponseMethod != null) {
                    onResponseMethod.run(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ServerManager", "error: *" + error.toString()+ "*");
                if(onErrorMethod != null) {
                    onErrorMethod.run(error);
                }
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.getBytes();
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
                    return result.getVal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return "";
                }
            }
        };
        managePost(param.toString(), new Lambda() {
            @Override
            public Object[] run(final Object... paramether) {
                AppDatabase.databaseWriteExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DatabaseUtil.getInstance().getRepositoryManager().loadNewData(AppDatabase.getDatabase(context), paramether[0].toString());
                            JSONObject obj = new JSONObject(paramether[0].toString());

                            // Save in the sharedPreferences the sessionid
                            SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.sessionId), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor= preferences.edit();
                            editor.putString("value", obj.getString("SessionId"));
                            editor.apply();

                            Util.getInstance().setSessionId(obj.getString("SessionId"));
                            Util.getInstance().setIdUser(obj.getLong("idUser"));

                            result.setVal(obj.getString("SessionId"));
                        } catch (JSONException | ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                            result.setVal("");
                        }
                        s.release();
                    }
                });
                return null;
            }
        }, new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                result.setVal("");
                Log.d("login", "Error on login");
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

        managePost(body.toString(), new Lambda() {
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


    /**
     * Download a file and save it in the directory
     * @param filename name of the file to download
     * @param directory Enviroment.something
     * @param receiver a lambda function (parameter[0]=boolean, paramether[1]=File)
     */
    public void downloadFile(final String filename, String directory, Lambda receiver){
        final String query="?to=fileManager&method=getFile&filename="+filename;
        File f = new File(context.getExternalFilesDir(directory), filename);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Could not download " + filename, Toast.LENGTH_SHORT).show();
            Log.d("naed", "Could not download " + filename);
        }

        TransferFile.downloadFile(SERVER+query, f, receiver);
    }

    public void uploadFile(final File file, long idResource, FileType type, Lambda receiver){
        final String[] format = file.getAbsolutePath().split("\\.");
        final String query="?to=fileManager&idSession="+Util.getInstance().getSessionId()+"&format="+format[format.length-1]+"&"+type.getKey()+"="+idResource;

        if(file.exists()){
            TransferFile.uploadFile(file, SERVER+query, receiver);
        } else{
            receiver.run(false);
        }
    }

    public void sendMessage(final FriendMessage message, Lambda onSuccess, Lambda onFailure){
        JSONObject body = new JSONObject();
        try {
            body.put("idSession", Util.getInstance().getSessionId());
            body.put("to", "friend");
            body.put("method", "addMessage");
            JSONObject data= new JSONObject();
            data.put("idFriendship", message.idFriendship);
            data.put("message", message.message);
            body.put("data", data);
            managePost(body.toString(), onSuccess, onFailure);
        } catch (JSONException e) {
            e.printStackTrace();
            onFailure.run();
        }
    }

    public void receiveMessage(Long idFriendship, Lambda onSuccess, Lambda onFailure){
        JSONObject body = new JSONObject();
        try {
            body.put("idSession", Util.getInstance().getSessionId());
            body.put("to", "friend");
            body.put("method", "getMessage");
            JSONObject data= new JSONObject();
            data.put("idFriendship", idFriendship);
            synchronized (lastDate){
                data.put("lastDate", lastDate.getVal());
            }
            body.put("data", data);
            managePost(body.toString(), onSuccess, onFailure);
        } catch (JSONException e) {
            e.printStackTrace();
            onFailure.run();
        }
    }

    public void startMessagePolling(long idFriendship){
        synchronized (pollingMessageOn) {
            pollingMessageOn.setVal(true);
        }
        synchronized (this.idFriendship) {
            this.idFriendship.setVal(idFriendship);

        }
        synchronized (this.lastDate){
            this.lastDate.setVal(0L);
        }
        runLoop();
    }

    private void runLoop(){
        synchronized (pollingMessageOn){
            if(!pollingMessageOn.getVal()){
                return;
            }
        }
        synchronized (idFriendship){
            receiveMessage(idFriendship.getVal(), new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    try {
                        JSONObject response = new JSONObject(paramether[0].toString());
                        synchronized (lastDate){
                            lastDate.setVal(response.getLong("lastDate"));
                        }
                        DatabaseUtil.getInstance().getRepositoryManager().getFriendRepository().loadData(response);
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runLoop();
                            }
                        }, 2000);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return new Object[0];
                }
            }, new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    return new Object[0];
                }
            });
        }

    }

    public void stopMessagePolling(){
        pollingMessageOn.setVal(false);
    }


}
