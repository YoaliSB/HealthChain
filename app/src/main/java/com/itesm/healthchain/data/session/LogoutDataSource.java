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

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles authentication logout.
 */
public class LogoutDataSource {
    private Context context;
    private static final String LOGOUT = "https://health-chain-api.herokuapp.com/api/logout";
    private RequestQueue requestQueue;
    LogoutStateListener logoutStateListener;

    public LogoutDataSource(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void logout() {
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, LOGOUT,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("respuesta", response.toString());
                logoutStateListener.onLogoutSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("LOGOUT", new String(error.networkResponse.data,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                logoutStateListener.onLogoutFailure();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authentication", SharedPreferencesManager.getToken(context));
                return headers;
            }
        };
        requestQueue.add(jsonObject);
    }

    public void setLogoutStateListener(LogoutStateListener stateListener) {
        this.logoutStateListener = stateListener;
    }
}
