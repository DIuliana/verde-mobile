package com.verde.ui.fragment;

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
import androidx.lifecycle.ViewModelProviders;

import com.example.verde.R;
import com.verde.data.Plant;
import com.verde.data.VerdeSocketProvider;
import com.verde.data.VerdeWebSocket;
import com.verde.ui.fragment.components.SpinnerAdapter;
import com.verde.ui.fragment.components.VerdeTextWatcher;
import com.verde.ui.model.AddPlantViewModel;
import com.verde.ui.model.PlantIdViewModel;
import com.verde.ui.model.WebSocketDataViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.navigation.Navigation.findNavController;


public class SavePlantFragment extends Fragment {

    private PlantIdViewModel plantIdViewModel;
    private AddPlantViewModel addPlantViewModel;
    private VerdeSocketProvider verdeSocketProvider;
    private WebSocketDataViewModel webSocketDataViewModel;
    private String plantName;
    private String plantSpecies;
    private String potId;
    private VerdeWebSocket webSocket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        plantIdViewModel = ViewModelProviders.of(getActivity()).get(PlantIdViewModel.class);
        addPlantViewModel = ViewModelProviders.of(this).get(AddPlantViewModel.class);
        webSocketDataViewModel = ViewModelProviders.of(getActivity()).get(WebSocketDataViewModel.class);
        verdeSocketProvider = ViewModelProviders.of(getActivity()).get(VerdeSocketProvider.class);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button saveButton = view.findViewById(R.id.savePlantButton);
        saveButton.setEnabled(false);
        EditText name = view.findViewById(R.id.plantName);
        Spinner species = view.findViewById(R.id.species);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        saveButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            plantName = name.getText().toString();
            plantSpecies = species.getSelectedItem().toString();
            savePlant();
            createWebSocket();
            findNavController(getView()).navigate(R.id.action_savePlant_to_homeFragment2);

        });

        species.setAdapter(new SpinnerAdapter(getContext(), R.layout.network_name_adapter, createSpeciesList()));
        name.addTextChangedListener(new VerdeTextWatcher(saveButton));

    }

    private void createWebSocket() {
        verdeSocketProvider.createWebSocket(potId, webSocketDataViewModel);
        Map<String, Boolean> justCreated = new HashMap<>();
        justCreated.put(potId, true);
        webSocketDataViewModel.setJustOpenedMap(justCreated);

    }

    private void savePlant() {
        potId = plantIdViewModel.getPotId();
        Plant plant = new Plant(potId, plantName, plantSpecies);
        addPlantViewModel.addPlant(plant);
    }

    private List<String> createSpeciesList() {

        List<String> species = new ArrayList<>();
        species.add("Choose species");
        species.add("Poinsettia");
        species.add("Snapdragon");
        species.add("Tiger orchid");
        return species;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_save_plant, container, false);
    }
}
