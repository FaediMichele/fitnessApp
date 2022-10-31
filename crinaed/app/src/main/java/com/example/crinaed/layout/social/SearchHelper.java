package com.example.crinaed.layout.social;

import android.content.Context;
import android.util.Log;

import com.example.crinaed.database.ServerManager;
import com.example.crinaed.database.TransferFile;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SearchHelper {
    private File searchDir;
    private ExecutorService service = TransferFile.getDownloaderExecutor();
    private Context context;
    public SearchHelper(File cache, Context context){
        searchDir = new File(cache, "searchData");
        this.context=context;
    }

    public void getFriendshipRequest(final Lambda onResultElaborated, final Lambda onFailure){
        service.submit(new Runnable() {
            @Override
            public void run() {
                ServerManager.getInstance(context).getFriendshipRequest(getLambda(onResultElaborated), onFailure);
            }
        });
    }

    public void search(final String text, final Lambda onResultElaborated){
        final String query = "to=search&method=social&text=" + text + "&idUser=" + Util.getInstance().getIdUser();
        service.submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                ServerManager.getInstance(context).manageGet(query, getLambda(onResultElaborated) , new Lambda() {
                    @Override
                    public Object[] run(Object... paramether) {
                        return new Object[0];
                    }
                });
                return null;
            }
        });
    }

    private Lambda getLambda(final Lambda onResultElaborated){
        return new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                Log.d("naed", paramether[0].toString());
                try {
                    JSONArray data = new JSONArray(paramether[0].toString());
                    for(int i = 0; i < data.length(); i++){
                        final JSONObject obj = data.getJSONObject(i);
                        String imageName = obj.getString("image");
                        if(!imageName.equals("")) {
                            Log.d("naed", "start image download");
                            ServerManager.getInstance(context).downloadFile(imageName, searchDir.getName(), new Lambda() {
                                @Override
                                public Object[] run(Object... paramether) {
                                    Log.d("naed", "image downloaded");
                                    onResultElaborated.run(obj, paramether[1]);
                                    return null;
                                }
                            });
                        } else{
                            onResultElaborated.run(obj);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return new Object[0];
            }
        };
    }


}
