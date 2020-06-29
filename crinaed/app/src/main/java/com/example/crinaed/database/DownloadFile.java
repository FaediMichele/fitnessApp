package com.example.crinaed.database;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Room;

import com.example.crinaed.util.Lambda;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.HttpsURLConnection;

public class DownloadFile {
    private static final int BUFFER_SIZE=1024;
    private static final int NUMBER_OF_THREAD = 4;
    private static final ExecutorService downloaderExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);


    public static void downloadFile(final String from, final File dest, final Lambda l){
        downloaderExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("naed", "start of executor");
                    downloadFile(new URL(from), dest);
                    l.run(true, dest);
                } catch (MalformedURLException e) {
                    l.run(false, dest);
                }

            }
        });
    }

    private static void downloadFile(URL from, File dest){
        InputStream stream = null;
        HttpURLConnection connection = null;
        BufferedOutputStream writer = null;
        try {
            connection = (HttpURLConnection) from.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            writer = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] buffer = new byte[BUFFER_SIZE];
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            stream = connection.getInputStream();
            if (stream != null) {
                int len=0;
                while ((len=stream.read(buffer))>=0){
                    writer.write(buffer,0,len);
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
