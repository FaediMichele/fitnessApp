package com.example.crinaed.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.crinaed.R;
import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.FriendMessage;
import com.example.crinaed.database.entity.Friendship;
import com.example.crinaed.database.entity.User;
import com.example.crinaed.database.entity.UserLevel;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Pair;
import com.example.crinaed.util.Single;
import com.example.crinaed.util.Util;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class ServerManager {
    private static final String SERVER = "http://192.168.1.5:8080";
    private static ServerManager instance;
    private Context context;
    private static final int MS_SOCKET_TIMEOUT = 100;
    private NetworkUtil networkUtil;


    private final Single<Boolean> pollingMessageOn = new Single<>(false);
    private final Single<Long> lastDate = new Single<>(0L);
    private final Single<Long> idFriendshipPolling = new Single<>(0L); // friendship messages polling
    private ServerManager(Context context){
        this.context=context;
        networkUtil = new NetworkUtil(context);
    }

    final Lambda loginLambda = new Lambda() {
        @Override
        public Object[] run(final Object... paramether) {
            final Lambda onDone = (Lambda) paramether[1];
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
                        editor.putLong("idUser", obj.getLong("idUser"));
                        editor.apply();

                        Util.getInstance().setSessionId(obj.getString("SessionId"));
                        Util.getInstance().setIdUser(obj.getLong("idUser"));

                        DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().createNewStepDone();

                        onDone.run(true, true, obj.getString("SessionId"));
                    } catch (Exception e) {
                        Log.d("naed", "message error : " +e.getMessage());
                        e.printStackTrace();
                        onDone.run(true,true,false, e);
                    }
                }
            });
            return null;
        }
    };


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
        }else if(context!=null){
            instance.context=context;
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
        }){
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
        Volley.newRequestQueue(this.context).add(request);
    }

    public void managePost(final String body, final Lambda onResponseMethod, final Lambda onErrorMethod){
        managePost(body, onResponseMethod, onErrorMethod, "");
    }
    public void managePost(final String body, final Lambda onResponseMethod, final Lambda onErrorMethod, final String query){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER+(query.equals("")?"":"?"+query), new Response.Listener<String>() {
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(MS_SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this.context).add(stringRequest);
    }


    public void login(String username, String password, final Lambda onDone) throws JSONException {
        if(!networkUtil.isConnected()) {
            onDone.run(false);
            return;
        }
        final JSONObject data = new JSONObject();
        final JSONObject param = new JSONObject();
        final Context context= this.context;
        password = Util.hash(password);
        data.put("email", username);
        data.put("hashPassword", password);

        param.put("to", "login");
        param.put("data", data);

        managePost(param.toString(), new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                loginLambda.run(paramether[0], onDone);
                return new Object[0];
            }
        }, new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                VolleyError error = (VolleyError) paramether[0];
                if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                    onDone.run(true, false, error);
                } else {
                    onDone.run(false, error);
                }
                Log.d("login", "Error on login: " + error.getMessage());
                return null;
            }
        });
    }

    public void logout(final Lambda onDone) throws JSONException{
        if(!networkUtil.isConnected()){
            onDone.run(false);
            return;
        }
        final JSONObject body = new JSONObject();
        body.put("to", "logout");
        body.put("idSession", Util.getInstance().getSessionId());
        DatabaseUtil.getInstance().getRepositoryManager().getData(new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                try {
                    body.put("data", paramether[0].toString());
                    managePost(body.toString(), new Lambda() {
                        @Override
                        public Object[] run(Object... paramether) {
                            Log.d("naed", "deleting all data");
                            Util.getInstance().deleteSharedPreferences(context);
                            DatabaseUtil.getInstance().getRepositoryManager().deleteAll(onDone);
                            //onDone.run(context.deleteDatabase(AppDatabase.DATABASE_NAME));
                            return new Object[0];
                        }
                    }, new Lambda() {
                        @Override
                        public Object[] run(Object... paramether) {
                            onDone.run(false);
                            return new Object[0];
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return new Object[0];
            }
        });
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

    /**
     * Download a file and save it in the directory
     * @param filename name of the file to download
     * @param directory Enviroment.something
     * @param receiver a lambda function (parameter[0]=boolean, paramether[1]=File)
     */
    public void downloadFile(final String filename, File directory, Lambda receiver){
        final String query="?to=fileManager&method=getFile&filename="+filename;
        File f = new File(directory, filename);
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

    public void blockUser(Long idUser, final Lambda onSuccess, Lambda onFailure){
        JSONObject body = new JSONObject();
        try {
            body.put("idSession", Util.getInstance().getSessionId());
            body.put("to", "friend");
            body.put("method", "blockUser");
            JSONObject data= new JSONObject();
            data.put("idFriend", idUser);
            body.put("data", data);
            managePost(body.toString(), new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    try {
                        Friendship friendship = new Friendship(new JSONObject(paramether[0].toString()));
                        DatabaseUtil.getInstance().getRepositoryManager().getFriendRepository().updateFriendship(friendship, onSuccess);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    onSuccess.run();
                    return null;
                }
            }, onFailure);
            stopMessagePolling();
        } catch (JSONException e) {
            e.printStackTrace();
            onFailure.run();
        }
    }

    public void unblockUser(final Long idUser, final Lambda onSuccess, Lambda onFailure){
        JSONObject body = new JSONObject();
        try {
            body.put("idSession", Util.getInstance().getSessionId());
            body.put("to", "friend");
            body.put("method", "addFriend");
            JSONObject data= new JSONObject();
            data.put("idFriend", idUser);
            body.put("data", data);
            managePost(body.toString(), new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    try {
                        Friendship friendship = new Friendship(new JSONObject(paramether[0].toString()));
                        DatabaseUtil.getInstance().getRepositoryManager().getFriendRepository().updateFriendship(friendship, onSuccess);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }, onFailure);
            stopMessagePolling();
        } catch (JSONException e) {
            e.printStackTrace();
            onFailure.run();
        }
    }

    public void sendRequestFriendship(long idFriend, final Lambda onSuccess, final Lambda onFailure){
        JSONObject body = new JSONObject();
        try {
            body.put("idSession", Util.getInstance().getSessionId());
            body.put("to", "friend");
            body.put("method", "requestFriendship");
            JSONObject data= new JSONObject();
            data.put("idReceiver", idFriend);
            body.put("data", data);
            managePost(body.toString(), new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    try{
                        JSONObject response = new JSONObject(paramether[0].toString());
                        JSONArray jsonArray = response.getJSONArray("friendLevels");
                        UserLevel[] levels = new UserLevel[jsonArray.length()];
                        for(int i=0; i<levels.length; i++){
                            levels[i] = new UserLevel(jsonArray.getJSONObject(i));
                        }
                        final User user = new User(response.getJSONObject("friend"));
                        DatabaseUtil.getInstance().getRepositoryManager().getUserRepository().addUser(user, levels).get();
                        Friendship friendship = new Friendship(response.getJSONObject("friendship"));
                        downloadFile(response.getJSONObject("friend").getString("image"), Environment.DIRECTORY_PICTURES, new Lambda() {
                            @Override
                            public Object[] run(Object... paramether) {
                                user.image = ((File) paramether[1]).getAbsolutePath();
                                user.imageDownloaded = true;
                                DatabaseUtil.getInstance().getRepositoryManager().getUserRepository().updateUser(user);
                                return new Object[0];
                            }
                        });
                        DatabaseUtil.getInstance().getRepositoryManager().getFriendRepository().addFriend(friendship, new Lambda() {
                            @Override
                            public Object[] run(Object... paramether) {
                                onSuccess.run(paramether);
                                return new Object[0];
                            }
                        });
                    } catch (JSONException ignore) {
                        ignore.printStackTrace();
                        Log.d("naed", "add friend response: " + paramether[0].toString());
                        onSuccess.run(true);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }, onFailure);
            stopMessagePolling();
        } catch (JSONException e) {
            e.printStackTrace();
            onFailure.run();
        }
    }

    public void getFriendshipRequest(final Lambda onSuccess, final Lambda onFailure) {
        JSONObject body = new JSONObject();
        try {
            body.put("idSession", Util.getInstance().getSessionId());
            body.put("to", "friend");
            body.put("method", "getFriendshipRequest");
            managePost(body.toString(), onSuccess, onFailure);
            stopMessagePolling();
        } catch (JSONException e) {
            e.printStackTrace();
            onFailure.run();
        }
    }


    public void buyCourse(long idCourse, final Lambda onSuccess, final Lambda onFailure) {
        JSONObject body = new JSONObject();
        try {
            body.put("idSession", Util.getInstance().getSessionId());
            body.put("to", "courseBought");
            body.put("method", "buyCourse");
            JSONObject data = new JSONObject();
            data.put("idCourse", idCourse);
            body.put("data", data);
            managePost(body.toString(), new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    try {
                        JSONObject response = new JSONObject(paramether[0].toString());
                        DatabaseUtil.getInstance().getRepositoryManager().getUserRepository().loadData(response).get();
                        DatabaseUtil.getInstance().getRepositoryManager().getSchoolRepository().loadData(response).get();
                        DatabaseUtil.getInstance().getRepositoryManager().getExerciseRepository().loadData(response).get();
                        DatabaseUtil.getInstance().getRepositoryManager().getReviewRepository().loadData(response).get();
                        onSuccess.run();
                    } catch (JSONException | InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        onFailure.run(e);
                    }
                    return new Object[0];
                }
            }, onFailure);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addReview(long idCourse, String text, int val, final Lambda onSuccess, final Lambda onFailure){
        JSONObject body = new JSONObject();
        try {
            body.put("idSession", Util.getInstance().getSessionId());
            body.put("to", "review");
            body.put("method", "addReview");
            JSONObject data = new JSONObject();
            data.put("idCourse", idCourse);
            data.put("text", text);
            data.put("val", val);
            body.put("data", data);
            managePost(body.toString(), new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    try {
                        JSONObject response = new JSONObject(paramether[0].toString());
                        DatabaseUtil.getInstance().getRepositoryManager().getReviewRepository().loadData(response).get();
                        onSuccess.run();
                    } catch (JSONException | InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        onFailure.run(e);
                    }
                    return new Object[0];
                }
            }, onFailure);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void archiveCourse(final Course course, final Lambda onSuccess, final Lambda onFailure){
        JSONObject body = new JSONObject();
        try {
            body.put("idSession", Util.getInstance().getSessionId());
            body.put("to", "courseBought");
            body.put("method", "archiveCourse");
            JSONObject data = new JSONObject();
            data.put("idCourse", course.idCourse);
            body.put("data", data);
            managePost(body.toString(), new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    try {
                        JSONObject response = new JSONObject(paramether[0].toString());
                        course.isArchived= response.getBoolean("value");
                        DatabaseUtil.getInstance().getRepositoryManager().getSchoolRepository().update(course);
                        onSuccess.run(course.isArchived);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        onFailure.run(e);
                    }
                    return new Object[0];
                }
            }, onFailure);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void createUser(String firstname, String surname, String email, String password, final Lambda onDone, final Lambda onFailure){
        String hashPassword = Util.hash(password);
        JSONObject body = new JSONObject();
        try {
            body.put("idSession", Util.getInstance().getSessionId());
            body.put("to", "user");
            body.put("method", "addUser");
            JSONObject data = new JSONObject();
            data.put("firstname", firstname);
            data.put("surname", surname);
            data.put("email", email);
            data.put("firstname", firstname);
            data.put("hashPassword", hashPassword);
            body.put("data", data);
            managePost(body.toString(), new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    loginLambda.run(paramether[0], onDone);
                    return new Object[0];
                }
            }, onFailure);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void startMessagePolling(long idFriendship){
        pollingMessageOn.setVal(true);
        this.idFriendshipPolling.setVal(idFriendship);
        this.lastDate.setVal(0L);
        runLoop();
    }

    private void runLoop(){
        if(!pollingMessageOn.getVal()){
            return;
        }
            receiveMessage(idFriendshipPolling.getVal(), new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    try {
                        JSONObject response = new JSONObject(paramether[0].toString());
                        lastDate.setVal(response.getLong("lastDate"));
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

    public void stopMessagePolling(){
        pollingMessageOn.setVal(false);
    }


}
