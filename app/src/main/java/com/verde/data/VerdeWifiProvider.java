package com.verde.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.stream.Collectors;

public class VerdeWifiProvider extends LiveData<List<String>> {

    private Context applicationContext;
    private WifiManager wifiManager;
    private ScanWifiReceiver scanWifiReceiver = new ScanWifiReceiver();

    public VerdeWifiProvider(Context applicationContext) {
        this.applicationContext = applicationContext;
        wifiManager = (WifiManager) applicationContext.getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    protected void onActive() {
        super.onActive();
        this.applicationContext.registerReceiver(scanWifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();

    }


    private List<String> extractVerdeSSID(List<ScanResult> wifiScanList) {
        List<String> verde = wifiScanList.stream().map(net -> net.SSID).filter(net -> net.contains("Verde")).collect(Collectors.toList());
        verde.add(0, "Select pot");
        return verde;
    }


    class ScanWifiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context c, Intent intent) {

            boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
            if (success) {
                postValue(extractVerdeSSID(wifiManager.getScanResults()));
                applicationContext.unregisterReceiver(scanWifiReceiver);
            } else {
                scanFailure();
            }

        }

        private void scanFailure() {
            //TODO - please try again button
        }

    }

    public ScanWifiReceiver getScanWifiReceiver() {
        return scanWifiReceiver;
    }

}
