package com.verde.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class VerdeHttpHelper {

    private static final String TAG = "VerdeHttpHelper";
    private static final String URL = "http://192.168.4.1/target-network";

    private RequestQueue queque;
    private VerdeResponseListener responseListener;
    private VerdeErrorResponseListener errorResponseListener;

    public VerdeHttpHelper(Context applicationContext) {
        queque = Volley.newRequestQueue(applicationContext);
        responseListener = new VerdeResponseListener();
        errorResponseListener = new VerdeErrorResponseListener();
    }

    public void sendCredentials(String pass, String ssid) {
        JSONObject requestBody = createRequest(ssid, pass);
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(URL, requestBody, responseListener, errorResponseListener);

        queque.add(jsonObjectRequest);

    }

    public void getInternetConnectionStatus() {
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(URL + "/status", null, responseListener, errorResponseListener);

        queque.add(jsonObjectRequest);

    }

    private static JSONObject createRequest(String ssid, String pass) {
        JSONObject req = new JSONObject();
        try {
            req.put("ssid", ssid);
            req.put("pass", pass);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }


        return req;
    }


    public VerdeResponseListener getResponseListener() {
        return responseListener;
    }

    public VerdeErrorResponseListener getErrorResponseListener() {
        return errorResponseListener;
    }

}
