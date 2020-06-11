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
import com.google.gson.Gson;
import com.itesm.healthchain.data.SharedPreferencesManager;
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
    private static final String INFO_COMPLETED = "https://health-chain-api.herokuapp.com/api/user/info_completed";
//    private static final String LOGIN = "https://en51dct0cvl9ag5.m.pipedream.net/api/login";
    private RequestQueue requestQueue;
    LoginStateListener loginStateListener;
    Gson gson;
    boolean isCompleted;

    public LoginDataSource(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        gson = new Gson();
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
                public Map<String, String> getHeaders() {
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

    public void fetchInfoIsCompleted() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, INFO_COMPLETED, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("INFO COMPLETED", response.toString());
                        try {
                            isCompleted = gson.fromJson(String.valueOf(response.getBoolean("completed_info")), boolean.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("INFO IS COMPLETED", error.toString());
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

    public boolean infoIsCompleted(){
        fetchInfoIsCompleted();
        return isCompleted;
    }

    public void updateInfoAsCompleted() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("initial_info", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, INFO_COMPLETED, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("INFO COMPLETED", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("INFO IS COMPLETED", error.toString());
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


    public void setLoginStateListener(LoginStateListener stateListener) {
        this.loginStateListener = stateListener;
    }
}
