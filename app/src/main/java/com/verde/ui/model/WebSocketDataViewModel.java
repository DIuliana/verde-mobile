package com.verde.ui.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;

import java.util.Map;

public class WebSocketDataViewModel extends AndroidViewModel {


    private MediatorLiveData<Map<String, String>> map = new MediatorLiveData<>();

    public WebSocketDataViewModel(@NonNull Application application) {
        super(application);
    }

    public MediatorLiveData<Map<String, String>> getMap() {
        return map;
    }

}
