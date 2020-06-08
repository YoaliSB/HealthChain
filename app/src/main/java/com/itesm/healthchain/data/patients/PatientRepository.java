package com.itesm.healthchain.data.patients;

import android.content.Context;

import com.itesm.healthchain.data.model.PatientInfo;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;

public class PatientRepository {
    private static volatile PatientRepository instance;
    private PatientNetworkDataSource patientNetworkDataSource;

    // private constructor : singleton access
    private PatientRepository(final Context context) {
        patientNetworkDataSource = new PatientNetworkDataSource(context);
    }

    public static PatientRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PatientRepository(context);
        }
        return instance;
    }

    public MutableLiveData<ArrayList<PatientInfo>> fetchPatients(){
        patientNetworkDataSource.fetchPatients();
        return patientNetworkDataSource.getPatients();
    }
}
