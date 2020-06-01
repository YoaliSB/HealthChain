package com.itesm.healthchain.data.model;

import java.util.List;

public class Patient {
    private PersonalData emergencyInfo;
    private List<MedicalRecordEntry> medicalRecord;
    private List<Prescription> prescriptions;

    public Patient(PersonalData emergencyInfo,
                   List<MedicalRecordEntry> medicalRecord,
                   List<Prescription> prescriptions) {
        this.emergencyInfo = emergencyInfo;
        this.medicalRecord = medicalRecord;
        this.prescriptions = prescriptions;
    }

    public PersonalData getEmergencyInfo() {
        return emergencyInfo;
    }

    public List<MedicalRecordEntry> getMedicalRecord() {
        return medicalRecord;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }
}
