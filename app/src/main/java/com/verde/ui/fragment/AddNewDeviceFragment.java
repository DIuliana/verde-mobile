package com.verde.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.verde.R;

import java.util.List;
import java.util.stream.Collectors;


public class AddNewDeviceFragment extends Fragment {

    private Button connectButton;
    private Spinner spinner;
    private EditText password;
    private ProgressBar progressBar;
    private List<String> wifiNetworks;

    private Context applicationContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationContext = getActivity().getApplicationContext();

        WifiManager wifiManager = (WifiManager) applicationContext.getSystemService(Context.WIFI_SERVICE);
        MyBroadcastReceiver receiver = new MyBroadcastReceiver(wifiManager, WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

        receiver.startScan();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUIComponents(view);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        progressBar.setVisibility(View.VISIBLE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_device, container, false);
    }


    private void initializeUIComponents(@NonNull View view) {
        connectButton = view.findViewById(R.id.connectButton);
        spinner = view.findViewById(R.id.SAPSpinner);
        password = view.findViewById(R.id.SAPPassword);
        progressBar = view.findViewById(R.id.progressBar);
    }


    class MyBroadcastReceiver extends BroadcastReceiver {

        private WifiManager wifiManager;
        String intentAction;


        public MyBroadcastReceiver(WifiManager wifiManager, String intentAction) {
            this.wifiManager = wifiManager;
            this.intentAction = intentAction;

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(intentAction);
            applicationContext.registerReceiver(this, intentFilter);
        }

        public boolean startScan() {
            return wifiManager.startScan();
        }

        @Override
        public void onReceive(Context c, Intent intent) {
            boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
            if (success) {
                wifiNetworks = getScanResults();

            } else {
                // scan failure handling
                scanFailure();
            }

            SpinnerAdapter adapter =
                    new SpinnerAdapter(applicationContext, R.layout.network_name_adapter, wifiNetworks);
            spinner.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }


        private List<String> getScanResults() {
            List<ScanResult> wifiScanList = wifiManager.getScanResults();

            List<String> wifiListToDisplay = extractVerdeSSID(wifiScanList);

            wifiListToDisplay.add(0, "Select pot");
            return wifiListToDisplay;
        }

        private List<String> extractVerdeSSID(List<ScanResult> wifiScanList) {

            return wifiScanList.stream().map(net -> net.SSID).filter(net -> net.contains("Verde")).collect(Collectors.toList());
        }

        private void scanFailure() {
            //TODO - please try again button
        }

    }

}
