package com.example.tflat_redo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.widget.Toast;

public class MyBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities == null)
                Toast.makeText(context, "No internet connection,please turn on wifi/mobile data", Toast.LENGTH_LONG).show();
            else {
                boolean isWifiConnected = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
                boolean isCellularConnected = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
                if(isWifiConnected && isCellularConnected) Toast.makeText(context, "WIFI:ON\nMOBILE DATA:ON", Toast.LENGTH_SHORT).show();
                if (isWifiConnected) Toast.makeText(context, "WIFI:ON", Toast.LENGTH_SHORT).show();
                else if (isCellularConnected)
                    Toast.makeText(context, "Mobile data:ON", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
