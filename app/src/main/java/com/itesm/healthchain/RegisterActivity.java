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
    private static final String url = "https://health-chain-api.herokuapp.com/api/register"; //Aqui va la url del servidor
    EditText etname,etemail,etpass,etpin,etphone,etpass2;
    Button signupbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etname =(EditText)findViewById(R.id.editname);
        etemail=(EditText)findViewById(R.id.editEmail);
        etpass=(EditText)findViewById(R.id.editPass);
        etpin=(EditText)findViewById(R.id.editPin);
        etphone=(EditText)findViewById(R.id.editPhone);
        etpass2=(EditText)findViewById(R.id.editPass2);
        signupbtn=(Button)findViewById(R.id.btnsignup);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name= etname.getText().toString().trim();
                final String email=etemail.getText().toString().trim();
                final String pass=etpass.getText().toString().trim();
                final String pin=etpin.getText().toString().trim();
                final String phone=etphone.getText().toString().trim();
                final String pass2=etpass.getText().toString().trim();

                JSONObject jsonBody = new JSONObject();
                if (pass.equals(pass2)) {
                    try {

                    jsonBody.put("name", name);
                    jsonBody.put("email", email);
                    jsonBody.put("phone", phone);
                    jsonBody.put("pin", pin);
                    jsonBody.put("password", pass);
                    jsonBody.put("password_confirmation", pass2);


                        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                            Log.i("respuesta", response.toString());
                            /*new Handler().postDelayed(new Runnable() {// a thread in Android
                                @Override
                                public void run() {
                                    Intent intent = new Intent( RegisterActivity.this, LoginActivity.class  );
                                    startActivity(intent);
                                    finish();
                                }
                            },3000);*/
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("ErrorB", error.toString());
                            Log.i("ErrorB", error.toString());
                            onBackPressed();

                        }
                    }) {
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("name",name);
                            params.put("email",email);
                            params.put("password",pass);
                            params.put("pin",pin);
                            params.put("phone",phone);
                            params.put("password_confirmation",pass2);
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
                    requestQueue.add(jsonOblect);

                    //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                } catch (JSONException e) {
                     e.printStackTrace();
                    }
                } else{
                    //TODO: MENSAJE DE CONTRASEÑA NO IGUAL
                }
            }
        });
    }
}