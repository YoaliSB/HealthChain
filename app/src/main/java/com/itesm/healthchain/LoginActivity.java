package com.itesm.healthchain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.healthchain.data.LoginDataSource;
import com.itesm.healthchain.data.UserRepository;
import com.itesm.healthchain.data.model.LoggedInUser;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements UserRepository.LoginStateListener {
    EditText editEmail, editPass;
    Button btnLogin;
    TextView tv;
    UserRepository repository;
    LoginDataSource dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dataSource = new LoginDataSource();
        repository = UserRepository.getInstance(dataSource, this);
        repository.setLoginListener(this);
        tv = findViewById(R.id.textSignUp);
        editEmail = findViewById(R.id.etemail);
        editPass = findViewById(R.id.etpass);
        btnLogin = findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = editEmail.getText().toString().trim();
                final String pass = editPass.getText().toString().trim();
                repository.login(email, pass, getApplicationContext());
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void redirect(String role) {
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

    @Override
    public void onLoginSuccess(LoggedInUser user) {
        redirect(user.getRole());
    }
}
