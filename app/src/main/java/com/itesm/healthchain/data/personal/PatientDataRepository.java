package com.itesm.healthchain.data.personal;

import android.content.Context;

import com.itesm.healthchain.data.model.Patient;
import com.itesm.healthchain.data.model.PersonalData;

import androidx.lifecycle.MutableLiveData;

public class PatientDataRepository implements EditPersonalDataListener{
    private static volatile PatientDataRepository instance;
    private PatientDataNetworkDataSource patientDataNetworkDataSource;
    private EditPersonalDataListener editPersonalDataListener;
    private String email = "";

    // private constructor : singleton access
    private PatientDataRepository(final Context context) {
        patientDataNetworkDataSource = new PatientDataNetworkDataSource(context);
        patientDataNetworkDataSource.setEditPersonalDataListener(this);
    }

    public static PatientDataRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PatientDataRepository(context);
        }
        return instance;
    }

    public MutableLiveData<Patient> fetchPatient() {
        patientDataNetworkDataSource.fetchPatient();
        return patientDataNetworkDataSource.getPatient();
    }

    public void fetchPersonalDataForDoctor(String email) {
        patientDataNetworkDataSource.fetchPatientForDoctor(email);
    }

    public MutableLiveData<Patient> subscribeForPatient() {
        return patientDataNetworkDataSource.getPatient();
    }

    public void updatePersonalData(PersonalData data) {
        patientDataNetworkDataSource.updatePersonalData(data);
    }

    public void setEditPersonalDataListener(EditPersonalDataListener editPersonalDataListener) {
        this.editPersonalDataListener = editPersonalDataListener;
    }

    @Override
    public void onEditFailure() {
        editPersonalDataListener.onEditFailure();
    }

    @Override
    public void onEditSuccess(PersonalData editedData) {
        editPersonalDataListener.onEditSuccess(editedData);
    }
}
