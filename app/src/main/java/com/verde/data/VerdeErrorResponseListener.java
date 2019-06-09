package com.verde.data;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class VerdeErrorResponseListener extends LiveData<HTTPCallsStatus> implements Response.ErrorListener {

    private final static String TAG = "VerdeErrorResponseListener";

    @Override
    public void onErrorResponse(VolleyError error) {

        NetworkResponse networkResponse = error.networkResponse;

        if (networkResponse != null) {
            String errorResp = new String(error.networkResponse.data);
            JSONObject jsonObject = getJsonErrorResp(errorResp);

            Log.i(TAG, jsonObject.toString());
            switch (networkResponse.statusCode) {
                case 500:
                case 404:
                    postValue(HTTPCallsStatus.FAILED);

            }
        }

    }

    private JSONObject getJsonErrorResp(String errorResp) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(errorResp);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return jsonObject;
    }
}
