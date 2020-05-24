package com.itesm.healthchain.data.session;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.itesm.healthchain.data.SharedPreferencesManager;
import com.itesm.healthchain.data.model.LoggedInUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PasswordDataSource {
    private Context context;
    private static final String URL = "https://health-chain-api.herokuapp.com/api/change_password";
    private RequestQueue requestQueue;
    LoginStateListener loginStateListener;
    private PasswordChangeStateListener passwordChangeListener;

    public PasswordDataSource(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void updatePassword(final String oldPass, final String newPass) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("password", oldPass);
            jsonBody.put("new_password", newPass);

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL,
                    jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("respuesta", response.toString());
                    passwordChangeListener.onChangeSuccess();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("UPDATE PASS", "" + error.networkResponse.statusCode);
                    passwordChangeListener.onChangeFailure();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", SharedPreferencesManager.getToken(context));
                    return headers;
                }
            };
            requestQueue.add(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setPasswordListener(PasswordChangeStateListener passwordChangeListener) {
        this.passwordChangeListener = passwordChangeListener;
    }


}
