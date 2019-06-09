package com.verde.ui.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class PlantIdViewModel extends AndroidViewModel {

    private String potId;

    public PlantIdViewModel(@NonNull Application application) {
        super(application);
    }


    public String getPotId() {
        return potId;
    }

    public void setPotId(String potId) {
        this.potId = potId;
    }

}
