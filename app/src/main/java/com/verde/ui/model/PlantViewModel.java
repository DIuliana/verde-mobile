package com.verde.ui.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class PlantViewModel extends AndroidViewModel {


    private String plantName;

    public PlantViewModel(@NonNull Application application) {
        super(application);
    }


    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }
}
