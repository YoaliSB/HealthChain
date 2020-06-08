package com.itesm.healthchain;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.healthchain.data.SharedPreferencesManager;
import com.itesm.healthchain.data.model.Doctor;
import com.itesm.healthchain.data.model.MedicalRecordEntry;
import com.itesm.healthchain.data.model.Patient;
import com.itesm.healthchain.data.model.PersonalData;
import com.itesm.healthchain.data.model.Prescription;
import com.itesm.healthchain.data.session.UserRepository;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        final UserRepository repository = new UserRepository(getApplicationContext());
        new Handler().postDelayed(new Runnable() {// a thread in Android
            @Override
            public void run() {

//                PersonalData data = new PersonalData();
//                ArrayList<MedicalRecordEntry> entries = new ArrayList<>();
//                entries.add( MedicalRecordEntry.createDummyEntry());
//                ArrayList<Prescription> list = new ArrayList<>();
//                list.add(Prescription.createDummyData());
//                Patient patient = new Patient(data, entries, list);
//                Gson gson = new GsonBuilder()
//                        .registerTypeAdapter(Prescription.class, new Prescription.PrescriptionTypeConverter())
//                        .registerTypeAdapter(MedicalRecordEntry.class, new MedicalRecordEntry.MedicalRecordEntryTypeConverter())
//                        .setPrettyPrinting()
//                        .create();
//                String json = gson.toJson(patient);
//                Log.d("JSON", json);
//                Patient p2 = gson.fromJson(json, Patient.class);

                if(repository.isLoggedIn()){
                    redirect(SharedPreferencesManager.getRole(getApplicationContext()));
                } else {
                    Intent intent = new Intent( SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },3000);
    }

    private void redirect(String role) {
        switch (role) {
            case "user":
                startActivity(new Intent(SplashActivity.this,
                        PatientActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                break;
            case "paramedic":
                startActivity(new Intent(SplashActivity.this,
                        ParamedicActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                break;
            case "doctor":
                startActivity(new Intent(SplashActivity.this,
                        DoctorActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                break;
            default:
                Toast.makeText(getApplicationContext(), R.string.role_error, Toast.LENGTH_LONG).show();
        }
    }
}