package com.itesm.healthchain;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.itesm.healthchain.data.model.PersonalData;
import com.itesm.healthchain.data.model.TagProfile;
import com.itesm.healthchain.nfc.NfcActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends NfcActivity {
    private static final String registerUrl = "https://health-chain-api.herokuapp.com/api/register";
    private static final String emergencyInfoUrl = "https://health-chain-api.herokuapp.com/api/user/emergency_info";

    EditText etname, etemail, etpass, etpass2;
    Button signupbtn;

    EditText etFullName, etBirthDate, etBloodType, etWeight, etHeight;
    EditText etHospital, etAilments, etAllergies;
    EditText etContactName, etContactPhone, etContactRelationship;
    Button submitProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etname = findViewById(R.id.editname);
        etemail = findViewById(R.id.editEmail);
        etpass = findViewById(R.id.editPass);
        etpass2 = findViewById(R.id.editPass2);
        signupbtn = findViewById(R.id.btnsignup);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        etFullName = findViewById(R.id.field_name);
        etBirthDate = findViewById(R.id.field_birthdate);
        etBloodType = findViewById(R.id.field_blood);
        etWeight = findViewById(R.id.field_weight);
        etHeight = findViewById(R.id.field_height);
        etHospital = findViewById(R.id.field_hospital);
        etAilments = findViewById(R.id.field_ailments);
        etAllergies = findViewById(R.id.field_allergies);
        etContactName = findViewById(R.id.field_contactName);
        etContactPhone = findViewById(R.id.field_contactPhone);
        etContactRelationship = findViewById(R.id.field_contactRelationship);
        submitProfileBtn = findViewById(R.id.button_submitEmergency);


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etname.getText().toString().trim();
                final String email = etemail.getText().toString().trim();
                final String pass = etpass.getText().toString().trim();
                final String pass2 = etpass2.getText().toString().trim();

                JSONObject jsonBody = new JSONObject();

                if (TextUtils.isEmpty(name) || name.length() < 3) {
                    etname.setError("Tu nombre debe ser de al menos 3 caracteres");
                    return;
                }
                if (TextUtils.isEmpty(email) || !email.matches(emailPattern)) {
                    etemail.setError("Ingresa un correo valido");
                    return;
                }
                if (TextUtils.isEmpty(pass) || pass.length() < 6) {
                    etpass.setError("Tu contraseña debe ser de al menos 6 caracteres");
                    return;
                }
                if (TextUtils.isEmpty(pass2) || !pass.equals(pass2)) {
                    etpass2.setError("Tu contraseña debe coincidir con la de arriba");
                    return;
                }

                try {
                    jsonBody.put("name", name);
                    jsonBody.put("email", email);
                    jsonBody.put("password", pass);
                    jsonBody.put("password_confirmation", pass2);

                    JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, registerUrl,
                            jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("respuesta", response.toString());
                            etFullName.setText(name);
                            setContentView(R.layout.create_emergency_profile);
                        /*


                         */
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
                        public Map<String, String> getHeaders() {
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

        submitProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etemail.getText().toString().trim();
                final String name = etFullName.getText().toString().trim();
                final String birthDate = etBirthDate.getText().toString().trim();
                final String bloodType = etBloodType.getText().toString().trim();
                final String weight = etWeight.getText().toString().trim();
                final String height = etHeight.getText().toString().trim();
                final String hospital = etHospital.getText().toString().trim();
                final String ailments = etAilments.getText().toString().trim();
                final String allergies = etAllergies.getText().toString().trim();
                final String contactName = etContactName.getText().toString().trim();
                final String contactPhone = etContactPhone.getText().toString().trim();
                final String contactRelationship = etContactRelationship.getText().toString().trim();

                JSONObject jsonBody = new JSONObject();

                // VALIDATE FIELDS

                try {
                    tagProfile = new TagProfile(
                            email, name, birthDate, bloodType, weight, height,
                            hospital, ailments, allergies,
                            contactName, contactPhone, contactRelationship);
                    PersonalData personalData = new PersonalData(tagProfile);
                    Gson gson = new Gson();
                    String content = gson.toJson(personalData);
                    jsonBody.put("content", content);

                    JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, emergencyInfoUrl,
                            jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("respuesta", response.toString());
                            isWriting = true;
                            setContentView(R.layout.nfc_screen);
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
                        public Map<String, String> getHeaders() {
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

    @Override
    protected void onNewIntent(Intent intent){
        if(isWriting){
            super.onNewIntent(intent);
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
    }
}