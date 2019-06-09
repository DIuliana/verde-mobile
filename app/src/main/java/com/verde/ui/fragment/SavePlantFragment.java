package com.verde.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.verde.R;
import com.verde.data.Plant;
import com.verde.ui.fragment.components.SpinnerAdapter;
import com.verde.ui.fragment.components.VerdeTextWatcher;
import com.verde.ui.model.AddPlantViewModel;
import com.verde.ui.model.PlantIdViewModel;

import java.util.ArrayList;
import java.util.List;

import static androidx.navigation.Navigation.findNavController;


public class SavePlantFragment extends Fragment {

    private PlantIdViewModel plantIdViewModel;
    private AddPlantViewModel addPlantViewModel;
    private String plantName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        plantIdViewModel = ViewModelProviders.of(getActivity()).get(PlantIdViewModel.class);
        addPlantViewModel = ViewModelProviders.of(this).get(AddPlantViewModel.class);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button saveButton = view.findViewById(R.id.savePlantButton);
        saveButton.setEnabled(false);
        saveButton.setOnClickListener(v -> {
            savePlant();
            findNavController(getView()).navigate(R.id.action_savePlant_to_homeFragment2);

        });

        Spinner species = view.findViewById(R.id.species);
        species.setAdapter(new SpinnerAdapter(getContext(), R.layout.network_name_adapter, createSpeciesList()));

        EditText name = view.findViewById(R.id.plantName);
        name.addTextChangedListener(new VerdeTextWatcher(saveButton));
        plantName = name.getText().toString();
    }

    private void savePlant() {
        Plant plant = new Plant(plantIdViewModel.getPotId(), plantName, getHumidityBySpecies());
        addPlantViewModel.addPlant(plant);
    }

    private double getHumidityBySpecies() {
        //TODO
        return 60;
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
