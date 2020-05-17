package com.itesm.healthchain;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String url = "https://health-chain-api.herokuapp.com/api/login";
    EditText editemail, editpass;
    Button btnLogin;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv = findViewById(R.id.textSignUp);
        editemail = findViewById(R.id.etemail);
        editpass = findViewById(R.id.etpass);
        btnLogin = findViewById(R.id.btnlogin);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = editemail.getText().toString().trim();
                final String pass = editpass.getText().toString().trim();

                JSONObject jsonBody = new JSONObject();
                if(TextUtils.isEmpty(email) || !email.matches(emailPattern))
                {
                    editemail.setError("Ingresa un correo valido");
                    return;
                }
                if(TextUtils.isEmpty(pass) || pass.length() < 6)
                {
                    editpass.setError("Contraseña incorrecta");
                    return;
                }

                try {
                    jsonBody.put("email", email);
                    jsonBody.put("password", pass);

                    JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url,
                            jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("respuesta", response.toString());
                            try {
                                String role = response.getJSONObject("data")
                                        .getJSONArray("roles")
                                        .getJSONObject(0)
                                        .getString("name");
                                login(role);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), getString(R.string.login_error),
                                    Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams(){
                            Map<String, String> params = new HashMap<>();
                            params.put("email", email);
                            params.put("password", pass);
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            final Map<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/json");
                            return headers;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                //METODO GET Este si funciona
                /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, LG, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("Response: ", response.toString());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error

                            }
                        });*/
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private void login(String role) {
        switch (role) {
            case "user":
                startActivity(new Intent(LoginActivity.this,
                        PatientActivity.class));
                break;
            case "paramedic":
                startActivity(new Intent(LoginActivity.this,
                        ParamedicActivity.class));
                break;
            case "doctor":
                startActivity(new Intent(LoginActivity.this,
                        DoctorActivity.class));
                break;
            default:
                Toast.makeText(getApplicationContext(), "whoops", Toast.LENGTH_LONG).show();
        }

    }
}
