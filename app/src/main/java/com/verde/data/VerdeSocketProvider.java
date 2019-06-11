package com.verde.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.verde.ui.model.WebSocketDataViewModel;

import java.util.HashMap;
import java.util.Map;

public class VerdeSocketProvider extends AndroidViewModel {

    private Map<String, VerdeWebSocket> wsHolder = new HashMap<>();


    public VerdeSocketProvider(@NonNull Application application) {
        super(application);
    }

    public VerdeWebSocket createWebSocket(String potId, WebSocketDataViewModel webSocketDataViewModel) {
        VerdeWebSocket verdeWebSocket = new VerdeWebSocket(potId, webSocketDataViewModel);
        wsHolder.put(potId, verdeWebSocket);
        return verdeWebSocket;
    }

    public VerdeWebSocket getSocketByPotId(String potId) {
        return wsHolder.get(potId);
    }
}
