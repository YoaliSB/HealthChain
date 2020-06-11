package com.itesm.healthchain.data.doctors;

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
import com.itesm.healthchain.data.model.PatientInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;

public class DoctorNetworkDataSource {
    private static final String GET_DOCTORS = "https://health-chain-api.herokuapp.com/api/user/my_doctors";
    private static final String POST_DOCTOR = "https://health-chain-api.herokuapp.com/api/user/my_doctors";
    private MutableLiveData<ArrayList<Doctor>> doctorMutableLiveData = new MutableLiveData<>();
    private Context context;
    RequestQueue requestQueue;
    DoctorDeleteListener doctorDeleteListener;
    Gson gson;

    public DoctorNetworkDataSource(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        gson = new Gson();
    }

    public void fetchDoctors() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, GET_DOCTORS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("DOCTORS", response.toString());
                        ArrayList<Doctor> doctors = new ArrayList<>();
                        // TODO: parse list of doctors and update viewModel
                        Type type = new TypeToken<ArrayList<Doctor>>() {}.getType();
                        try {
                            doctors = gson.fromJson(String.valueOf(response.getJSONArray("my_doctors")), type);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        doctorMutableLiveData.postValue(doctors);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("DOCTORS", error.toString());
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

    public MutableLiveData<ArrayList<Doctor>> getDoctors() {
        return doctorMutableLiveData;
    }

    public void updateDoctor(final Doctor doctor) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("doctor_id", doctor.getId());
            jsonBody.put("active", doctor.isActive());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, POST_DOCTOR, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("POST DOCTORS", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("DOCTORS", error.toString());
                doctorDeleteListener.onFailure();
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

    public void setDoctorDeleteListener(DoctorDeleteListener listener) {
        this.doctorDeleteListener = listener;
    }
}
