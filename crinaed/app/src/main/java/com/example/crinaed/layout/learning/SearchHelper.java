package com.example.crinaed.layout.learning;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.database.TransferFile;
import com.example.crinaed.util.Category;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;


public class SearchHelper {
    private ExecutorService service = TransferFile.getDownloaderExecutor();
    private Context context;

    public SearchHelper(Context context){
        this.context=context;
    }

    public void search(String text, final Lambda onDone){
        final String query = "to=search&method=learning&text=" + text + "&idUser=" + Util.getInstance().getIdUser();
        service.submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                DatabaseUtil.getInstance().getRepositoryManager().getSchoolRepository().deleteOldSearch().get();
                ServerManager.getInstance(context).manageGet(query, getLambda(onDone) , new Lambda() {
                    @Override
                    public Object[] run(Object... paramether) {
                        return new Object[0];
                    }
                });
                return null;
            }
        });
    }

    private Lambda getLambda(final Lambda onDone){
        return new Lambda() {
            @Override
            public Object[] run(Object... paramether) {
                try {
                    Log.d("naed", "search data: " + paramether[0].toString());
                    JSONObject response = new JSONObject((paramether[0].toString()));
                    JSONArray courses = response.getJSONArray("Course");
                    long[] downloadCourses= new long[courses.length()];
                    for(int i =0; i<downloadCourses.length; i++){
                        downloadCourses[i] = courses.getJSONObject(i).getLong("idCourse");
                    }

                    DatabaseUtil.getInstance().getRepositoryManager().getUserRepository().loadData(response).get();
                    DatabaseUtil.getInstance().getRepositoryManager().getSchoolRepository().loadData(response).get();
                    DatabaseUtil.getInstance().getRepositoryManager().getReviewRepository().loadData(response).get();
                    onDone.run(downloadCourses);
                } catch (JSONException | InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                return new Object[0];
            }
        };
    }
}
