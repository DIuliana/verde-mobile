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
import com.verde.data.HTTPCallsStatus;
import com.verde.data.VerdeHttpHelper;
import com.verde.data.VerdeWifiProvider;
import com.verde.ui.fragment.components.SpinnerAdapter;
import com.verde.ui.fragment.components.VerdeTextWatcher;

import java.util.List;
import java.util.stream.Collectors;

import static androidx.navigation.Navigation.findNavController;


public class InternetConnectionFragment extends Fragment {

    private Spinner spinner;
    private ProgressBar progressBar;
    private Context applicationContext;
    private EditText pass;
    private VerdeHttpHelper httpHelper;
    private int count = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationContext = getActivity().getApplicationContext();

        VerdeWifiProvider verdeWifiProvider = new VerdeWifiProvider(applicationContext);
        verdeWifiProvider.observe(this, wifis -> {
            spinner.setAdapter(new SpinnerAdapter(applicationContext, R.layout.network_name_adapter, extractNonVerdeWifi(wifis)));
            progressBar.setVisibility(View.INVISIBLE);
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_provide_internet_connection, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button connectButton = view.findViewById(R.id.internetConnButton);
        pass = view.findViewById(R.id.internetPass);
        pass.addTextChangedListener(new VerdeTextWatcher(connectButton));
        spinner = view.findViewById(R.id.internetSpinner);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        connectButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            httpHelper = new VerdeHttpHelper(applicationContext);
            httpHelper.sendCredentials(pass.getText().toString(), spinner.getSelectedItem().toString());

            httpHelper.getErrorResponseListener().observe(this, this::handleErrors);
            httpHelper.getResponseListener().observe(this, this::handleResponse);

        });

    }

    private void handleResponse(HTTPCallsStatus status) {

        switch (status) {
            case RECEIVED:
                httpHelper.getInternetConnectionStatus();
                break;
            case CONNECTION_FAILED:
                progressBar.setVisibility(View.VISIBLE);
                if (count == 5) {
                    count = 1;
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(applicationContext, "Please try again", Toast.LENGTH_SHORT).show();
                    break;
                }
                httpHelper.getInternetConnectionStatus();
                count++;
                break;
            case CONNECTED:
                progressBar.setVisibility(View.INVISIBLE);
                findNavController(getView()).navigate(R.id.action_successPotConnection_to_savePlant);
                break;
        }
    }

    private void handleErrors(HTTPCallsStatus status) {

        switch (status) {
            case FAILED:
                Toast.makeText(applicationContext, "Please try again", Toast.LENGTH_SHORT).show();
                break;

        }

    }


    private List<String> extractNonVerdeWifi(List<String> wifis) {
        List<String> res = wifis.stream().filter(wifi -> !wifi.contains("Verde")).collect(Collectors.toList());
        res.add(0, "Select internet connection");

        return res;
    }
}
