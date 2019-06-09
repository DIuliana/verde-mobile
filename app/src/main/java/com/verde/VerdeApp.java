package com.verde;

import android.app.Application;

import com.verde.persist.PlantRepository;


public class VerdeApp extends Application {

    public PlantRepository getPlantRepo() {
        return new PlantRepository(this);
    }
}
