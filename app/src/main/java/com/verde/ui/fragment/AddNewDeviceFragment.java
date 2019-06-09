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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.verde.R;
import com.verde.data.VerdeWifiConnector;
import com.verde.data.VerdeWifiProvider;
import com.verde.data.WifiStatus;

import java.util.List;
import java.util.stream.Collectors;

import static androidx.navigation.Navigation.findNavController;


public class AddNewDeviceFragment extends Fragment {

    private Spinner spinner;
    private ProgressBar progressBar;
    private Context applicationContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationContext = getActivity().getApplicationContext();

        VerdeWifiProvider verdeWifiProvider = new VerdeWifiProvider(applicationContext);
        verdeWifiProvider.observe(this, wifi -> {
            spinner.setAdapter(new SpinnerAdapter(applicationContext, R.layout.network_name_adapter, extractVerdeWifi(wifi)));
            progressBar.setVisibility(View.INVISIBLE);
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button connectButton = view.findViewById(R.id.connectButton);
        EditText pass = view.findViewById(R.id.SAPPassword);
        connectButton.setOnClickListener(v -> {
            VerdeWifiConnector connector = new VerdeWifiConnector(applicationContext);
            connector.observe(this, this::processWifiResponse);
            connector.connectWifi(spinner.getSelectedItem().toString(), pass.getText().toString());
        });
        spinner = view.findViewById(R.id.SAPSpinner);
        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_device, container, false);
    }

    private List<String> extractVerdeWifi(List<String> wifis) {
        List<String> res = wifis.stream().filter(wifi -> wifi.contains("Verde")).collect(Collectors.toList());
        res.add(0, "Select your pot");
        return res;
    }


    private void processWifiResponse(WifiStatus wifiStatus) {

        switch (wifiStatus) {

            case CONNECTED:
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(applicationContext, "Pot connected!", Toast.LENGTH_SHORT).show();
                findNavController(getView()).navigate(R.id.action_addNewDevice_to_internetConnection);
                break;
            case CONNECTING:
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(applicationContext, "Connecting...", Toast.LENGTH_SHORT).show();
                break;
            case NETWORK_NOT_FOUND:
                Toast.makeText(applicationContext, "Could not connect. Please try again!", Toast.LENGTH_SHORT).show();

        }
    }

}
