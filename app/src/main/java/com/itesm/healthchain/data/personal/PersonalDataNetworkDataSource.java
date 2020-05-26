package com.itesm.healthchain.data.personal;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.itesm.healthchain.data.SharedPreferencesManager;
import com.itesm.healthchain.data.model.Patient;
import com.itesm.healthchain.data.model.PersonalData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;

public class PersonalDataNetworkDataSource {
    private static final String MY_INFO = "https://health-chain-api.herokuapp.com/api/user/my_info";
    private static final String UPDATE_INFO = "https://health-chain-api.herokuapp.com/api/user/emergency_info";
    private MutableLiveData<PersonalData> personalDataMutableLiveData = new MutableLiveData<>();
    private Context context;
    private RequestQueue requestQueue;
    private EditPersonalDataListener editPersonalDataListener;
    private Gson gson;

    public PersonalDataNetworkDataSource(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        gson = new Gson();
    }

    public void fetchPersonalData() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MY_INFO, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("PERSONAL DATA", response.toString());
                        PersonalData data = new PersonalData();
                        // TODO: parse complete patient object, change viewmdels
                        try {
                            data = gson.fromJson(String.valueOf(response.getJSONObject("user")
                                    .getJSONObject("_patient_info")
                                    .getJSONObject("emergencyInfo")), PersonalData.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        personalDataMutableLiveData.postValue(data);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PERSONAL DATA", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>(super.getHeaders());
                headers.put("Content-Type", "application/json");
                headers.put("Authorization",
                        SharedPreferencesManager.getToken(context));
                // TODO: on auth failure send back to login
                return headers;
            }
        };
        requestQueue.add(request);
    }

    public MutableLiveData<PersonalData> getPersonalData() {
        return personalDataMutableLiveData;
    }

    public void updatePersonalData(final PersonalData data) {
        String emergencyInfo = gson.toJson(data);
        JSONObject jsonBody = new JSONObject();
        try {
            JSONObject json = new JSONObject(emergencyInfo);
            jsonBody.put("content", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, UPDATE_INFO, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("EDIT PERSONAL DATA", response.toString());
                        personalDataMutableLiveData.postValue(data);
                        editPersonalDataListener.onEditSuccess(data);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PERSONAL DATA", error.toString());
                editPersonalDataListener.onEditFailure();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>(super.getHeaders());
                headers.put("Content-Type", "application/json");
                headers.put("Authorization",
                        SharedPreferencesManager.getToken(context));
                // TODO: on auth failure send back to login
                return headers;
            }
        };
        requestQueue.add(request);
    }

    public void setEditPersonalDataListener(EditPersonalDataListener editPersonalDataListener) {
        this.editPersonalDataListener = editPersonalDataListener;
    }
}
