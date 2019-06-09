package com.verde.ui.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.verde.VerdeApp;
import com.verde.data.Plant;
import com.verde.persist.PlantRepository;

import java.util.List;

public class PlantListViewModel extends AndroidViewModel {


    private PlantRepository plantRepository;
    private MediatorLiveData<List<Plant>> plants = new MediatorLiveData<List<Plant>>();


    public PlantListViewModel(@NonNull Application application) {
        super(application);
        plantRepository = ((VerdeApp) application).getPlantRepo();
        getAllPlants();
    }

    private void getAllPlants() {
        plants.addSource(plantRepository.getAllPlants(), dbPlants -> plants.postValue(dbPlants));

    }

    public LiveData<List<Plant>> getPlants() {
        return plants;
    }

}