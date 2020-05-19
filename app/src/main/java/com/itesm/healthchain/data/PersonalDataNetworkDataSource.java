package com.itesm.healthchain.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.itesm.healthchain.R;
import com.itesm.healthchain.data.model.PersonalData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;

public class PersonalDataNetworkDataSource {
    private static final String MY_INFO = "https://health-chain-api.herokuapp.com/api/user/my_info";
    private MutableLiveData<PersonalData> personalDataMutableLiveData = new MutableLiveData<>();
    private Context context;

    public PersonalDataNetworkDataSource(Context context) {
        this.context = context;
    }

    public void fetchPersonalData() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MY_INFO, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("PERSONAL DATA", response.toString());
                        PersonalData data = new PersonalData();
                        try {
                            String name = response.getJSONObject("user").getString("_name");
                            data.setName(name);
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public MutableLiveData<PersonalData> getPersonalData() {
        return personalDataMutableLiveData;
    }
}
