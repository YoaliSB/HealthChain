package com.itesm.healthchain.data.personal;

import android.content.Context;

import com.itesm.healthchain.data.model.MedicalRecordEntry;
import com.itesm.healthchain.data.model.PersonalData;
import com.itesm.healthchain.data.model.Prescription;

import java.util.List;

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

    public MutableLiveData<PersonalData> fetchPersonalData() {
        personalDataNetworkDataSource.fetchPersonalData();
        return personalDataNetworkDataSource.getPersonalData();
    }

    public MutableLiveData<PersonalData> fetchPersonalDataForDoctor(String email) {
        if (!this.email.equals(email)) {
            this.email = email;
            personalDataNetworkDataSource.fetchPersonalDataForDoctor(email);
        }
        return personalDataNetworkDataSource.getPersonalData();
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
