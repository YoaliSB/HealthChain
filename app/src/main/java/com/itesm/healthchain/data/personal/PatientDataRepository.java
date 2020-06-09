package com.itesm.healthchain.data.personal;

import android.content.Context;

import com.itesm.healthchain.data.model.MedicalRecordEntry;
import com.itesm.healthchain.data.model.Patient;
import com.itesm.healthchain.data.model.PersonalData;

import androidx.lifecycle.MutableLiveData;

public class PatientDataRepository implements EditPersonalDataListener, EditMedicalRecordListener{
    private static volatile PatientDataRepository instance;
    private PatientDataNetworkDataSource patientDataNetworkDataSource;
    private EditPersonalDataListener editPersonalDataListener;
    private EditMedicalRecordListener editMedicalRecordListener;

    // private constructor : singleton access
    private PatientDataRepository(final Context context) {
        patientDataNetworkDataSource = new PatientDataNetworkDataSource(context);
        patientDataNetworkDataSource.setEditPersonalDataListener(this);
        patientDataNetworkDataSource.setEditMedicalRecordListener(this);
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

    public void setEditMedicalRecordListener(EditMedicalRecordListener editMedicalRecordListener) {
        this.editMedicalRecordListener = editMedicalRecordListener;
    }

    @Override
    public void onEditFailure() {
        editPersonalDataListener.onEditFailure();
    }

    @Override
    public void onEditRecordSuccess(String email) {
        editMedicalRecordListener.onEditRecordSuccess(email);
    }

    @Override
    public void onEditRecordFailure() {
        editMedicalRecordListener.onEditRecordFailure();
    }

    @Override
    public void onEditSuccess(PersonalData editedData) {
        editPersonalDataListener.onEditSuccess(editedData);
    }

    public void updateMedicalRecord(String email, MedicalRecordEntry newEntry) {
        patientDataNetworkDataSource.updateMedicalRecord(email, newEntry);
    }
}
