package com.example.crinaed.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.FileUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Room;

import com.example.crinaed.util.Lambda;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.HttpsURLConnection;

public class TransferFile {
    private static final int BUFFER_SIZE=1024;
    private static final int NUMBER_OF_THREAD = 4;
    private static final ExecutorService downloaderExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);

    public static void uploadFile(final File from, final String dest, final Lambda l){
        downloaderExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    l.run(uploadFile(from, new URL(dest)), from);
                } catch (MalformedURLException e) {
                    l.run(false, from);
                }
            }
        });
    }



    public static void downloadFile(final String from, final File dest, final Lambda l){
        downloaderExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    l.run(downloadFile(new URL(from), dest), dest);
                } catch (MalformedURLException e) {
                    l.run(false, dest);
                }
            }
        });
    }


    private static boolean uploadFile(final File from, @NotNull final URL dest){
        boolean ret;
        HttpURLConnection connection = null;
        BufferedInputStream reader = null;
        DataOutputStream request = null;
        try {
            connection = (HttpURLConnection) dest.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Length", String.valueOf(from.length()));
            connection.setRequestProperty("Content-Type", "video/mp4");

            request = new DataOutputStream(connection.getOutputStream());
            reader = new BufferedInputStream(new FileInputStream(from));
            byte[] buffer = new byte[BUFFER_SIZE];

            int len;
            while((len=reader.read(buffer))>=0){
                request.write(buffer,0,len);
            }

            request.flush();
            request.close();

            int respCode = connection.getResponseCode();
            ret = respCode==200;

        } catch (IOException ioException) {
            ret=false;
            ioException.printStackTrace();
        } finally {

            try {
                if(request != null){
                    request.close();
                }
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                ret=false;
                e.printStackTrace();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return ret;
    }

    private static boolean downloadFile(URL from, File dest){
        boolean ret=true;
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
            ret=false;
            ioException.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                    writer.close();
                } catch (IOException e) {
                    ret=false;
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return ret;
    }
}
