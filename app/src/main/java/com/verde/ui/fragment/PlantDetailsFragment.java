package com.verde.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.verde.R;
import com.verde.data.VerdeSocketProvider;
import com.verde.data.VerdeWebSocket;
import com.verde.ui.fragment.components.VerdeTextWatcher;
import com.verde.ui.model.PlantIdViewModel;
import com.verde.ui.model.PlantViewModel;


public class PlantDetailsFragment extends Fragment {

    private VerdeSocketProvider verdeSocketProvider;
    private PlantIdViewModel plantIdViewModel;
    private PlantViewModel plantViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        verdeSocketProvider = ViewModelProviders.of(getActivity()).get(VerdeSocketProvider.class);
        plantIdViewModel = ViewModelProviders.of(getActivity()).get(PlantIdViewModel.class);
        plantViewModel = ViewModelProviders.of(getActivity()).get(PlantViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plant_details, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText humidity = view.findViewById(R.id.humidity);
        TextView actualHumidity = view.findViewById(R.id.detail_humi);
        TextView name = view.findViewById(R.id.plantNameDetails);
        name.setText(plantViewModel.getPlantName());
        Button sendHumidity = view.findViewById(R.id.sendHumidity);
        humidity.addTextChangedListener(new VerdeTextWatcher(sendHumidity));

        String potId = plantIdViewModel.getPotId();
        if (verdeSocketProvider.getSocketByPotId(potId) == null) {
            verdeSocketProvider.createWebSocket(potId);
        }

        VerdeWebSocket socket = verdeSocketProvider.getSocketByPotId(potId);

        socket.getMessage().observe(this, message -> actualHumidity.setText(message));


        sendHumidity.setOnClickListener(v -> {
            String newHumidity = humidity.getText().toString();
            socket.sendTarget(newHumidity);

            Toast.makeText(getContext(), "Sent humidity: " + newHumidity, Toast.LENGTH_SHORT).show();

        });


    }
}
