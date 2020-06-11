package com.itesm.healthchain.data.patients;

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
import com.google.gson.reflect.TypeToken;
import com.itesm.healthchain.data.SharedPreferencesManager;
import com.itesm.healthchain.data.model.Doctor;
import com.itesm.healthchain.data.model.Patient;
import com.itesm.healthchain.data.model.PatientInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;

public class PatientNetworkDataSource {
    private static final String GET_PATIENTS = "https://health-chain-api.herokuapp.com/api/doctor/get_patients";
    private MutableLiveData<ArrayList<PatientInfo>> patientMutableLiveData = new MutableLiveData<ArrayList<PatientInfo>>();
    private Context context;
    private RequestQueue requestQueue;
    private Gson gson;

    public PatientNetworkDataSource(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        gson = new Gson();
    }

    public void fetchPatients() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, GET_PATIENTS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("PATIENTS", response.toString());
                        ArrayList<PatientInfo> patients = new ArrayList<>();
                        // TODO: parse list of patients and update viewModel
                        Type type = new TypeToken<ArrayList<PatientInfo>>() {}.getType();
                        try {
                            patients = gson.fromJson(String.valueOf(response.getJSONArray("my_patients")), type);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        patientMutableLiveData.postValue(patients);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PATIENTS", error.toString());
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

    public MutableLiveData<ArrayList<PatientInfo>> getPatients() {
        return patientMutableLiveData;
    }
}
