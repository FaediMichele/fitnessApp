package com.example.crinaed.database;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.crinaed.R;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Single;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class FileManager {

    public enum FileType{
        EXERCISE("idExercise"), COURSE("idCourse");
        private String name;
        FileType(String name){
            this.name=name;
        }

        public String getKey() {
            return name;
        }
    }

    public enum Format{
        IMAGE(".jpg", "addImage"), VIDEO(".mp4", "addVideo");

        private String format;
        private String method;
        Format(String s, String method){
            this.format=s;
            this.method=method;
        }

        public String getMethod() {
            return method;
        }

        public String getFormat() {
            return format;
        }
    }
    
    private Context context;
    public FileManager(Context context){
        this.context=context;
    }
    
    public void saveFile(String url, final String filename, final String d, final Lambda response){
        File f = new File(context.getExternalFilesDir(d), filename);
        try {
            if(f.createNewFile()){
                Log.d("naed", "created new file");
            }
            DownloadFile.downloadFile(url, f, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String extractFile(String filename, String d){
        File dir = new File(context.getFilesDir(), d);
        File file = new File(dir, filename);
        if(file.exists()) {
            try {
                return fileToString(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private String fileToString(File file) throws Exception {
        FileInputStream fin = new FileInputStream(file);
        String ret = convertStreamToString(fin);
        fin.close();
        return ret;
    }

    private String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
    
}
