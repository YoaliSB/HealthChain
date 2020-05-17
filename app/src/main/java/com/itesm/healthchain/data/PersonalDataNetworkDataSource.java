package com.itesm.healthchain.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.itesm.healthchain.R;
import com.itesm.healthchain.data.model.PersonalData;

import org.json.JSONObject;

import java.util.Map;

import androidx.lifecycle.MutableLiveData;

public class PersonalDataNetworkDataSource {
    private static final String MY_INFO = "https://health-chain-api.herokuapp.com/api/my_info";
    private MutableLiveData<PersonalData> personalDataMutableLiveData;
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
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: handle errors
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                SharedPreferences preferences = context.getSharedPreferences(
                        context.getString(R.string.preferences_key), Context.MODE_PRIVATE);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization",
                        preferences.getString(context.getString(R.string.token_key), ""));
                return headers;
            }
        };
    }

    public MutableLiveData<PersonalData> getPersonalData() {
        return personalDataMutableLiveData;
    }
}
