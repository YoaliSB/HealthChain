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
import com.itesm.healthchain.data.model.LoggedInUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private Context context;
    private static final String LOGIN = "https://health-chain-api.herokuapp.com/api/login";
    private RequestQueue requestQueue;
    LoginStateListener loginStateListener;

    public LoginDataSource(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void login(final String email, final String password) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, LOGIN,
                    jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("respuesta", response.toString());
                    try {
                        String token = response.getString("token");
                        String id = response.getJSONObject("data").getString("id");
                        String name = response.getJSONObject("data").getString("name");
                        String mail = response.getJSONObject("data").getString("email");
                        String role = response.getJSONObject("data")
                                .getJSONArray("roles")
                                .getJSONObject(0)
                                .getString("name");
                       loginStateListener.onLoginSuccess(new LoggedInUser(id, name, mail, role, token));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   Log.e("LOGIN", "" + error.networkResponse.statusCode);
                   loginStateListener.onLoginFailure();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            requestQueue.add(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setLoginStateListener(LoginStateListener stateListener) {
        this.loginStateListener = stateListener;
    }
}
