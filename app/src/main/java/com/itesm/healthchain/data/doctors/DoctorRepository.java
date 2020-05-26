package com.itesm.healthchain.data.doctors;

import android.content.Context;

import com.itesm.healthchain.data.model.Doctor;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;

public class DoctorRepository implements DoctorDeleteListener{
    private static volatile DoctorRepository instance;
    private DoctorNetworkDataSource doctorNetworkDataSource;
    private DoctorDeleteListener listener;

    // private constructor : singleton access
    private DoctorRepository(final Context context) {
        doctorNetworkDataSource = new DoctorNetworkDataSource(context);
        doctorNetworkDataSource.setDoctorDeleteListener(this);
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

    public void deleteDoctor(Doctor doctor){
        doctor.setIsActive(false);
        doctorNetworkDataSource.updateDoctor(doctor);
    }

    public void setListener(DoctorDeleteListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDelete(Doctor deletedDoctor) { }

    @Override
    public void onFailure() {
        this.listener.onFailure();
    }
}
