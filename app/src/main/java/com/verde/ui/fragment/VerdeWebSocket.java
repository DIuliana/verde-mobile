package com.verde.ui.fragment;

import com.verde.ui.model.WebSocketDataViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import tech.gusavila92.websocketclient.WebSocketClient;

class VerdeWebSocket extends WebSocketClient {

    private static final String WS_PROTOCOL = "ws://";
    private static final String WS_HOST = "03f33535.ngrok.io";
    private static final String WS_ENDPOINT = "/verde/socket/mobile/";
    private WebSocketDataViewModel webSocketDataViewModel;
    private String potId;
    private HashMap<String, String> map = new HashMap<>();
    private JSONObject targetHumidity;

    public VerdeWebSocket(String potId, WebSocketDataViewModel webSocketDataViewModel) {
        super(createUri(potId));
        setConnectTimeout(10000);
        setReadTimeout(60000);
        enableAutomaticReconnection(5000);
        connect();
        this.webSocketDataViewModel = webSocketDataViewModel;
        this.potId = potId;
    }

    private static URI createUri(String potId) {
        URI uri = null;
        try {
            uri = new URI(WS_PROTOCOL + WS_HOST + WS_ENDPOINT + potId);
        } catch (
                URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }

    @Override
    public void onOpen() {
        System.out.println("onOpen");
        createTargetHumidityRequest();
        this.send(targetHumidity.toString());
    }

    //TODO send actual target_humidity
    private void createTargetHumidityRequest() {
        targetHumidity = new JSONObject();
        try {
            targetHumidity.put("target_humidity", "60");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTextReceived(String message) {
        System.out.println("onTextReceived: " + message);

        //TODO parse message
        map.put(potId, message);
        webSocketDataViewModel.getMap().postValue(map);
    }

    @Override
    public void onBinaryReceived(byte[] data) {
        System.out.println("onBinaryReceived");
    }

    @Override
    public void onPingReceived(byte[] data) {
        System.out.println("onPingReceived");
    }

    @Override
    public void onPongReceived(byte[] data) {
        System.out.println("onPongReceived");
    }

    @Override
    public void onException(Exception e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void onCloseReceived() {
        System.out.println("onCloseReceived");
    }
}

