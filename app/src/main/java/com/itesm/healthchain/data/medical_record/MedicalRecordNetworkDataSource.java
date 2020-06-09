package com.itesm.healthchain.data.medical_record;

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
import com.itesm.healthchain.data.model.MedicalRecordEntry;
import com.itesm.healthchain.data.model.Patient;
import com.itesm.healthchain.data.model.PersonalData;
import com.itesm.healthchain.data.model.Prescription;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;

public class MedicalRecordNetworkDataSource {
    private static final String UPDATE_MEDICAL_RECORD = "https://health-chain-api.herokuapp.com//api/doctor/update_user";

    private MutableLiveData<Patient> patientDataMutableLiveData = new MutableLiveData<>();
    private Context context;
    private RequestQueue requestQueue;
    private EditMedicalRecordListener editMedicalRecordListener;
    private Gson gson;

    public MedicalRecordNetworkDataSource(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        gson = new Gson();
    }

    public MutableLiveData<Patient> getPatient() {
        return patientDataMutableLiveData;
    }

    public void updateMedicalRecord(String email, final List<MedicalRecordEntry> medicalRecordEntryList, final List<Prescription> prescriptionList) {
        String medicalRecord = gson.toJson(medicalRecordEntryList);
        String prescriptions = gson.toJson(prescriptionList);
        JSONObject jsonBody = new JSONObject();
        JSONObject content = new JSONObject();
        try {
            JSONObject record = new JSONObject(medicalRecord);
            JSONObject prescrips = new JSONObject(prescriptions);
            content.put("medicalRecord", record);
            content.put("prescriptions", prescrips);
            jsonBody.put("email", email);
            jsonBody.put("content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, UPDATE_MEDICAL_RECORD, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("EDIT MEDICAL RECORD", response.toString());
                        editMedicalRecordListener.onEditSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("EDIT PERSONAL DATA", error.toString());
                editMedicalRecordListener.onEditFailure();
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

    public void setEditMedicalRecordListener(EditMedicalRecordListener editMedicalRecordListener) {
        this.editMedicalRecordListener = editMedicalRecordListener;
    }
}
