package com.itesm.healthchain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String url2 = "https://jsonplaceholder.typicode.com/posts";
    // server url
    private static final String url = "https://health-chain-api.herokuapp.com/api/register";
    EditText etname, etemail, etpass, etpass2;
    Button signupbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etname = findViewById(R.id.editname);
        etemail = findViewById(R.id.editEmail);
        etpass = findViewById(R.id.editPass);
        etpass2 = findViewById(R.id.editPass2);
        signupbtn = findViewById(R.id.btnsignup);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name= etname.getText().toString().trim();
                final String email = etemail.getText().toString().trim();
                final String pass = etpass.getText().toString().trim();
                final String pass2 = etpass.getText().toString().trim();

                JSONObject jsonBody = new JSONObject();
                if (!pass.equals(pass2)) {
                    // TODO: Non-matching password message
                }
                try {
                jsonBody.put("name", name);
                jsonBody.put("email", email);
                jsonBody.put("password", pass);
                jsonBody.put("password_confirmation", pass2);

                    JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url,
                            jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("respuesta", response.toString());
                        new Handler().postDelayed(new Runnable() {// a thread in Android
                            @Override
                            public void run() {
                                Intent intent = new Intent( RegisterActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },1500);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ErrorB", error.toString());
                        Toast.makeText(getApplicationContext(), getString(R.string.register_error),
                                Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        final Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObject);

            } catch (JSONException e) {
                 e.printStackTrace();
                }
            }
        });
    }
}