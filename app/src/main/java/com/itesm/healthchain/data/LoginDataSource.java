package com.itesm.healthchain.data;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.itesm.healthchain.R;
import com.itesm.healthchain.data.model.LoggedInUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private static final String ngrok = "https://health-chain-api.herokuapp.com/api/login";
    private final MutableLiveData<LoggedInUser> user = new MutableLiveData<>();

    public MutableLiveData<LoggedInUser> subscribe() {
        return user;
    }

    public void login(final String email, final String password, final Context context) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, ngrok,
                    jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("respuesta", response.toString());
                    try {
                        String role = response.getJSONObject("data")
                                .getJSONArray("roles")
                                .getJSONObject(0)
                                .getString("name");

                        user.setValue(new LoggedInUser("1", "Juanito", "juanito@mail.com", role, "123"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   Log.e("LOGIN", "" + error.networkResponse.statusCode);
                }
            }) {
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonObject);

        } catch (JSONException e) {
            //TODO
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
