package com.verde.ui.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.verde.VerdeApp;
import com.verde.data.Plant;
import com.verde.persist.PlantRepository;

public class AddPlantViewModel extends AndroidViewModel {

    private PlantRepository plantRepository;

    public AddPlantViewModel(@NonNull Application application) {
        super(application);
        plantRepository = ((VerdeApp) application).getPlantRepo();
    }

    public void addPlant(Plant plant) {
        plantRepository.add(plant);
    }

}
