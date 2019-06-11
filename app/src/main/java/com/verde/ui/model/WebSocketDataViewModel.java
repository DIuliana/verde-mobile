package com.verde.ui.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;

import java.util.HashMap;
import java.util.Map;

public class WebSocketDataViewModel extends AndroidViewModel {


    private Map<String, Boolean> justOpenedMap = new HashMap<>();
    private MediatorLiveData<Map<String, String>> messagesMap = new MediatorLiveData<>();

    public WebSocketDataViewModel(@NonNull Application application) {
        super(application);
    }

    public MediatorLiveData<Map<String, String>> getMessagesMap() {
        return messagesMap;
    }

    public Map<String, Boolean> getJustOpenedMap() {
        return justOpenedMap;
    }

    public void setJustOpenedMap(Map<String, Boolean> justOpenedMap) {
        this.justOpenedMap = justOpenedMap;
    }
}
