package com.itesm.healthchain.data.medical_record;

import android.content.Context;

import com.itesm.healthchain.data.model.MedicalRecordEntry;
import com.itesm.healthchain.data.model.Prescription;

import java.util.List;

public class MedicalRecordRepository implements EditMedicalRecordListener{
    private static volatile MedicalRecordRepository instance;
    private MedicalRecordNetworkDataSource medicalRecordNetworkDataSource;
    private EditMedicalRecordListener editMedicalRecordListener;

    // private constructor : singleton access
    private MedicalRecordRepository(final Context context) {
        medicalRecordNetworkDataSource = new MedicalRecordNetworkDataSource(context);
        medicalRecordNetworkDataSource.setEditMedicalRecordListener(this);
    }

    public static MedicalRecordRepository getInstance(Context context) {
        if (instance == null) {
            instance = new MedicalRecordRepository(context);
        }
        return instance;
    }

    public void updateMedicalRecord(String email, List<MedicalRecordEntry> medicalRecordEntryList, List<Prescription> prescriptionList) {
        medicalRecordNetworkDataSource.updateMedicalRecord(email, medicalRecordEntryList, prescriptionList);
    }

    public void setEditMedicalRecordListener(EditMedicalRecordListener editMedicalRecordListener) {
        this.editMedicalRecordListener = editMedicalRecordListener;
    }

    @Override
    public void onEditFailure() {
        editMedicalRecordListener.onEditFailure();
    }

    @Override
    public void onEditSuccess() {
        editMedicalRecordListener.onEditSuccess();
    }
}
