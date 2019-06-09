package com.verde.persist;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.verde.data.Plant;

import java.util.List;

public class PlantRepository {

    private PlantDao plantDao;

    public PlantRepository(Application application) {
        PlantDatabase plantDatabase = PlantDatabase.getInstance(application);
        plantDao = plantDatabase.productDao();
    }


    public LiveData<List<Plant>> getAllPlants() {
        return plantDao.getAll();
    }

    public void add(Plant plant) {
        plantDao.insert(plant);
    }

    public LiveData<Plant> findPlant(String id) {
        return plantDao.find(id);
    }


}
