package com.verde.ui.fragment;

import android.content.Context;
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
import androidx.lifecycle.LifecycleRegistry;

import com.example.verde.R;
import com.verde.data.VerdeWifiProvider;


public class AddNewDeviceFragment extends Fragment {

    private Button connectButton;
    private Spinner spinner;
    private EditText password;
    private ProgressBar progressBar;

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context applicationContext = getActivity().getApplicationContext();

        VerdeWifiProvider verdeWifiProvider = new VerdeWifiProvider(applicationContext);

        verdeWifiProvider.observe(this, verdeWifi -> {
            spinner.setAdapter(new SpinnerAdapter(applicationContext, R.layout.network_name_adapter, verdeWifi));
            progressBar.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        connectButton = view.findViewById(R.id.connectButton);
        spinner = view.findViewById(R.id.SAPSpinner);
        password = view.findViewById(R.id.SAPPassword);
        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_device, container, false);
    }

    public LifecycleRegistry getLifecycleRegistry() {
        return lifecycleRegistry;
    }


    //    connectButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            applicationContext.unregisterReceiver(receiver);
//
//            String ssid = spinner.getSelectedItem().toString();
//            Editable key = password.getText();
//
//
//            WifiConfiguration wifiConfiguration = new WifiConfiguration();
//            wifiConfiguration.SSID = String.format("\"%s\"", ssid);
//            wifiConfiguration.preSharedKey = String.format("\"%s\"", key);
//
//            wifiManager.disconnect();
//            int netId = wifiManager.addNetwork(wifiConfiguration);
//            if (netId == -1) {
//                netId = getNetworkId(ssid);
//            }
//            wifiManager.enableNetwork(netId, true);
//            boolean isConnected = wifiManager.reconnect();
//            if (!isConnected) {
//                Toast.makeText(applicationContext,
//                        "Could not connect. Please try again!",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//    });

}
