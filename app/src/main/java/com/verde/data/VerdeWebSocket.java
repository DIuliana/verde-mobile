package com.verde.data;

import com.verde.ui.model.WebSocketDataViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import tech.gusavila92.websocketclient.WebSocketClient;

public class VerdeWebSocket extends WebSocketClient {

    private static final String WS_PROTOCOL = "ws://";
    private static final String WS_HOST = "71ddba2e.ngrok.io";
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

        Boolean aBoolean = webSocketDataViewModel.getJustOpenedMap().get(potId);
        if (aBoolean!=null && aBoolean) {
           sendTargetHumidity("what_ever");
        } else
            this.send("Hi! here is mobile " + potId);
    }

    @Override
    public void onTextReceived(String message) {
        System.out.println("onTextReceived: " + message);

        //TODO parse message
        map.put(potId, message);
        webSocketDataViewModel.getMessagesMap().postValue(map);
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


    public void sendTargetHumidity(String plantSpecies) {
        System.out.println("sendTargetHumidity ");
        int targetHumidityOfSpecies = getTargetHumidityOfSpecies(plantSpecies);
        JSONObject targetHumidityRequest = createTargetHumidityRequest(String.valueOf(targetHumidityOfSpecies));
        this.send(targetHumidityRequest.toString());
    }

    //TODO repo
    private int getTargetHumidityOfSpecies(String plantSpecies) {
        return 60;
    }

    //TODO send actual target_humidity
    private JSONObject createTargetHumidityRequest(String targetHumidity) {
        JSONObject targetHumidityJSON = new JSONObject();
        try {
            targetHumidityJSON.put("target_humidity", targetHumidity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return targetHumidityJSON;
    }


}

