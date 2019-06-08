package com.verde.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;

import static android.net.wifi.WifiManager.NETWORK_STATE_CHANGED_ACTION;
import static android.net.wifi.WifiManager.SUPPLICANT_STATE_CHANGED_ACTION;
import static android.net.wifi.WifiManager.WIFI_STATE_CHANGED_ACTION;

public class VerdeWifiConnector extends LiveData<WifiStatus> {


    private static final String TAG = "VerdeWifiConnector";
    private Context applicationContext;

    private WifiManager wifiManager;
    private WifiStatus wifiStatus;
    private String verdePass = "";
    private String verdeSSID = "";
    private boolean disconnecting;
    private String previousNetworkSSID;

    WifiConnectionReceiver wifiConnectionReceiver = new WifiConnectionReceiver();

    public VerdeWifiConnector(Context applicationContext) {
        this.applicationContext = applicationContext;
        wifiManager = (WifiManager) applicationContext.getSystemService(Context.WIFI_SERVICE);
    }


    @Override
    protected void onActive() {
        super.onActive();
        this.applicationContext.registerReceiver(wifiConnectionReceiver, getFilterForWifiConnection());
    }

    private IntentFilter getFilterForWifiConnection() {
        IntentFilter intentFilter = new IntentFilter(WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(SUPPLICANT_STATE_CHANGED_ACTION);
        return intentFilter;
    }


    public void connectWifi(String ssid, String pass) {
        if (TextUtils.isEmpty(ssid) || TextUtils.isEmpty(pass)) {
            Log.i(TAG, "onReceive: cannot use connection without passing in a proper wifi SSID and password.");
            postValue(WifiStatus.NETWORK_NOT_FOUND);
        }

        verdeSSID = ssid;
        verdePass = pass;
        wifiStatus = WifiStatus.CONNECTING;

        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiInfo currentConnection = wifiManager.getConnectionInfo();

        if (!currentConnection.getSSID().equals(verdeSSID)) {
            wifiManager.disconnect();
            connectToWifi();
        }

    }

    private void connectToWifi() {

        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "\"" + verdeSSID + "\"";
        wifiConfiguration.preSharedKey = "\"" + verdePass + "\"";
        int i = wifiManager.addNetwork(wifiConfiguration);

        if (i == -1) {
            postValue(WifiStatus.NETWORK_NOT_FOUND);
        }
        wifiManager.enableNetwork(i, true);

    }


    class WifiConnectionReceiver extends BroadcastReceiver {

        public static final String TAG = "WifiConnectionReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {

                if (SUPPLICANT_STATE_CHANGED_ACTION.equals(action)) {

                    WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                    String currentSSID = connectionInfo.getSSID();
                    currentSSID = currentSSID.replace("\"", "");

                    Log.i(TAG, "WiFi current SSID: [" + currentSSID + "]");

                    if (verdeSSID.equals(currentSSID)) {

                        if (isConnected()) {
                            Log.i(TAG, "WiFi connected to: [ " + verdeSSID + "]");
                            wifiStatus = WifiStatus.CONNECTED;
                            applicationContext.unregisterReceiver(wifiConnectionReceiver);
                        } else {
                            wifiStatus = WifiStatus.CONNECTING;
                        }
                        postValue(wifiStatus);
                    }
                }

            }
        }

        private boolean isConnected() {

            boolean b = wifiManager.getConnectionInfo().getNetworkId() != -1;

            if (b) {
                Log.i(TAG, wifiManager.getConnectionInfo() + "connected: ");
            }
            return b;
        }

    }
}
