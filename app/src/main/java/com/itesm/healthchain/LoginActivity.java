package com.itesm.healthchain;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String LG = "https://jsonplaceholder.typicode.com/posts/1"; //URL para pruebas de get
    private static final String url2 = "https://jsonplaceholder.typicode.com/posts"; //URL para pruebas de post
    private static final String url = "https://localhost:3000/api/login"; //URL para pruebas de post
    EditText editemail,editpass;
    Button btnLogin;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv=(TextView)findViewById(R.id.textSignUp);
        editemail=(EditText)findViewById(R.id.etemail);
        editpass=(EditText)findViewById(R.id.etpass);
        btnLogin=(Button)findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=editemail.getText().toString().trim();
                final String pass=editpass.getText().toString().trim();

                JSONObject jsonBody = new JSONObject();

                try {
                    jsonBody.put("email", email);
                    jsonBody.put("password", pass);
                    /*
                    jsonBody.put("name", "abc@abc.com");
                    jsonBody.put("email", "");
                    jsonBody.put("phone", "");
                    jsonBody.put("pin", "");
                    jsonBody.put("password", "");
                    */

                    JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, url2, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                            Log.i("respuesta", response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            onBackPressed();

                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            final Map<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/json");
                            return headers;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(jsonOblect);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                //METODO GET
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

                //METODO POST
                /*StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.i("Response", response);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                //Log.i("Error.Response", response);
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("title", "bar");
                        params.put("body", "foo");
                        params.put("userId", "1");
                        return params;
                    }
                };

                /*StringRequest stringRequest=new StringRequest(Request.Method.GET, LG, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object=new JSONObject(response);
                            Log.i("objeto", response);
                            if (!object.getBoolean("sucsses")){
                                final String name=object.getString("name");
                                final String emaildata=object.getString("email");
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("username",name);
                                intent.putExtra("email",emaildata);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(),"User Login UnSucssesFull", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String,String>params=new HashMap<String, String>();
                        params.put("email",email);
                        params.put("password",pass);
                        params.put("Content-Type", "application/json");
                        return params;
                    }
                };*/
                //RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                //requestQueue.add(jsonObjectRequest);
                //requestQueue.add(postRequest);

                //Log.i("prueba", String.valueOf(stringRequest));
                //startActivity(new Intent(LoginActivity.this,MainActivity.class));*/
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }
}
