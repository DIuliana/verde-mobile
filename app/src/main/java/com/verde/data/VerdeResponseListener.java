package com.verde.data;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class VerdeResponseListener extends LiveData<HTTPCallsStatus> implements Response.Listener<JSONObject> {

    private final static String TAG = "VerdeResponseListener";

    @Override
    public void onResponse(JSONObject response) {

        Object resp = getResponse(response);

        if (resp != null) {

            if ("Received".equals(resp.toString())) {
                postValue(HTTPCallsStatus.RECEIVED);

            } else if (!resp.toString().equals("3")) {
                postValue(HTTPCallsStatus.CONNECTION_FAILED);
            } else {
                postValue(HTTPCallsStatus.CONNECTED);
            }

        }

    }


    private Object getResponse(JSONObject response) {
        Object resp = null;
        Log.i(TAG, "Got response: " + response);
        try {
            resp = response.get("resp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resp;
    }


}
