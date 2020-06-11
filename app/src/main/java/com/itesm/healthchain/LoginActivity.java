package com.itesm.healthchain;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.healthchain.data.session.LoginStateListener;
import com.itesm.healthchain.data.session.UserRepository;
import com.itesm.healthchain.data.model.LoggedInUser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements LoginStateListener {
    EditText editEmail, editPass;
    Button btnLogin;
    TextView tv;
    UserRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        repository = new UserRepository(getApplicationContext());
        repository.setLoginListener(this);
        tv = findViewById(R.id.textSignUp);
        editEmail = findViewById(R.id.etemail);
        editPass = findViewById(R.id.etpass);
        btnLogin = findViewById(R.id.btnlogin);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = editEmail.getText().toString().trim();
                final String pass = editPass.getText().toString().trim();
                if(TextUtils.isEmpty(email) || !email.matches(emailPattern)) {
                    editEmail.setError("Ingresa un correo valido");
                    return;
                }
                if(TextUtils.isEmpty(pass) || pass.length() < 6) {
                    editPass.setError("Contraseña incorrecta");
                    return;
                }
                repository.login(email, pass);
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
//                if(repository.infoIsCompleted()){
//                    startActivity(new Intent(LoginActivity.this,
//                            PatientActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
//                } else {
//                    startActivity(new Intent(LoginActivity.this,
//                            RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
//                }
                startActivity(new Intent(LoginActivity.this,
                        PatientActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                break;
            case "paramedic":
                startActivity(new Intent(LoginActivity.this,
                        ParamedicActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                break;
            case "doctor":
                startActivity(new Intent(LoginActivity.this,
                        DoctorActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                break;
            default:
                Toast.makeText(getApplicationContext(), R.string.role_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoginSuccess(@Nullable LoggedInUser user) {
        if (user != null) {
            redirect(user.getRole());
        }
    }

    @Override
    public void onLoginFailure() {
        Toast.makeText(getApplicationContext(), R.string.login_error, Toast.LENGTH_LONG).show();
    }
}
