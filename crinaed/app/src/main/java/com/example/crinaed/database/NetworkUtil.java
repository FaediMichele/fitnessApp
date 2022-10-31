package com.example.crinaed.database;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.crinaed.util.Pair;

public class NetworkUtil {
    private boolean isConnected;
    private static NetworkUtil instance;
    private Context context;
    private boolean old;

    public static NetworkUtil getInstance() {
        return instance;
    }

    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback(){
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            isConnected=true;
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            isConnected=false;
        }
    };
    public NetworkUtil(Context context){
        this.context=context;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                connectivityManager.registerDefaultNetworkCallback(networkCallback);
                isConnected=true;
            } else{
                old=true;
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                isConnected = networkInfo !=null && networkInfo.isConnected();
            }
        } else{
            isConnected=false;
        }
    }


    public boolean isConnected() {
        if(old){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager==null){
                return false;
            }
            final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo !=null && networkInfo.isConnected();
        }
        return isConnected;
    }
}
