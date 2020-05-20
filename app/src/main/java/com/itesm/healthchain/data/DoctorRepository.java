package com.itesm.healthchain.data;

import android.content.Context;

import com.itesm.healthchain.data.model.Doctor;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;

public class DoctorRepository {
    private static volatile DoctorRepository instance;
    private DoctorNetworkDataSource doctorNetworkDataSource;

    // private constructor : singleton access
    private DoctorRepository(final Context context) {
        doctorNetworkDataSource = new DoctorNetworkDataSource(context);
    }

    public static DoctorRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DoctorRepository(context);
        }
        return instance;
    }

    public MutableLiveData<ArrayList<Doctor>> fetchDoctors(){
        doctorNetworkDataSource.fetchDoctors();
        return doctorNetworkDataSource.getDoctors();
    }

}
