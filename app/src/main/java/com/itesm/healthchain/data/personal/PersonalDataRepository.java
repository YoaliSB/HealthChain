package com.itesm.healthchain.data.personal;

import android.content.Context;

import com.itesm.healthchain.data.model.Patient;
import com.itesm.healthchain.data.model.PersonalData;

import androidx.lifecycle.MutableLiveData;

public class PersonalDataRepository implements EditPersonalDataListener{
    private static volatile PersonalDataRepository instance;
    private PersonalDataNetworkDataSource personalDataNetworkDataSource;
    private EditPersonalDataListener editPersonalDataListener;
    private String email = "";

    // private constructor : singleton access
    private PersonalDataRepository(final Context context) {
        personalDataNetworkDataSource = new PersonalDataNetworkDataSource(context);
        personalDataNetworkDataSource.setEditPersonalDataListener(this);
    }

    public static PersonalDataRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PersonalDataRepository(context);
        }
        return instance;
    }

    public MutableLiveData<Patient> fetchPatient() {
        personalDataNetworkDataSource.fetchPatient();
        return personalDataNetworkDataSource.getPatient();
    }

    public void fetchPersonalDataForDoctor(String email) {
        personalDataNetworkDataSource.fetchPatientForDoctor(email);
    }

    public MutableLiveData<Patient> subscribeForPatient() {
        return personalDataNetworkDataSource.getPatient();
    }

    public void updatePersonalData(PersonalData data) {
        personalDataNetworkDataSource.updatePersonalData(data);
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
