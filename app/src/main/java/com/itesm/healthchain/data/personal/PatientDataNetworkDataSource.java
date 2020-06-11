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
import com.google.gson.GsonBuilder;
import com.itesm.healthchain.data.SharedPreferencesManager;
import com.itesm.healthchain.data.model.MedicalRecordEntry;
import com.itesm.healthchain.data.model.Patient;
import com.itesm.healthchain.data.model.PersonalData;
import com.itesm.healthchain.data.model.Prescription;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;

public class PatientDataNetworkDataSource {
    private static final String MY_INFO = "https://health-chain-api.herokuapp.com/api/user/my_info";
    private static final String UPDATE_INFO = "https://health-chain-api.herokuapp.com/api/user/emergency_info";
    private static final String GET_PATIENT_INFO = "https://health-chain-api.herokuapp.com/api/doctor/show_user";
    private static final String UPDATE_MEDICAL_RECORD = "https://health-chain-api.herokuapp.com/api/doctor/update_user";

    private MutableLiveData<Patient> patientDataMutableLiveData = new MutableLiveData<>();
    private Context context;
    private RequestQueue requestQueue;
    private EditPersonalDataListener editPersonalDataListener;
    private EditMedicalRecordListener editMedicalRecordListener;
    private Gson gson;

    public PatientDataNetworkDataSource(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        gson = new GsonBuilder()
            .registerTypeAdapter(Prescription.class, new Prescription.PrescriptionTypeConverter())
            .registerTypeAdapter(MedicalRecordEntry.class, new MedicalRecordEntry.MedicalRecordEntryTypeConverter())
            .setPrettyPrinting()
            .create();
    }

    public void fetchPatient() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MY_INFO, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("PERSONAL DATA", response.toString());
                        Patient data = null;
                        // TODO: parse complete patient object, change viewmdels
                        try {
                            data = gson.fromJson(String.valueOf(response.getJSONObject("user")
                                    .getJSONObject("_patient_info")), Patient.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        patientDataMutableLiveData.postValue(data);
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

    public MutableLiveData<Patient> getPatient() {
        return patientDataMutableLiveData;
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
                        editPersonalDataListener.onEditSuccess(data);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("EDIT PERSONAL DATA", error.toString());
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

    public void fetchPatientForDoctor(String email) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, GET_PATIENT_INFO, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("PATIENT DATA", response.toString());
                        Patient data = null;
                        try {
                            data = gson.fromJson(String.valueOf(response.getJSONObject("user")
                                    .getJSONObject("_patient_info")),  Patient.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        patientDataMutableLiveData.postValue(data);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PATIENT DATA", error.toString());
                patientDataMutableLiveData.postValue(Patient.getDummyPatient());
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


    public void updateMedicalRecord(final String email, MedicalRecordEntry newEntry) {
        final Patient currentPatient = patientDataMutableLiveData.getValue();
        currentPatient.addMedicalRecordEntry(newEntry);
        List<MedicalRecordEntry> medicalRecord = currentPatient.getMedicalRecord();
        List<Prescription> prescriptions = currentPatient.getPrescriptions();

        String strRecord = gson.toJson(medicalRecord);
        String strPrescriptions = gson.toJson(prescriptions);
        JSONObject jsonBody = new JSONObject();
        JSONObject content = new JSONObject();
        try {
            JSONArray record = new JSONArray(strRecord);
            JSONArray prescrips = new JSONArray(strPrescriptions);
            content.put("medicalRecord", record);
            content.put("prescriptions", prescrips);
            jsonBody.put("email", "david@email.com");
            jsonBody.put("content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, UPDATE_MEDICAL_RECORD, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("EDIT MEDICAL RECORD", response.toString());
                        patientDataMutableLiveData.postValue(currentPatient);
                        editMedicalRecordListener.onEditRecordSuccess(email);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("EDIT MEDICAL RECORD", error.toString());
                editMedicalRecordListener.onEditRecordFailure();
//                patientDataMutableLiveData.postValue(currentPatient);
//                editMedicalRecordListener.onEditRecordSuccess(email);
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

    public void setEditPersonalDataListener(EditPersonalDataListener editPersonalDataListener) {
        this.editPersonalDataListener = editPersonalDataListener;
    }
}
