package com.itesm.healthchain.data.model;

import java.util.ArrayList;
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

    public static Patient getDummyPatient() {
        ArrayList<MedicalRecordEntry> record = new ArrayList<>();
        record.add(MedicalRecordEntry.createDummyEntry());

        ArrayList<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(Prescription.createDummyData());
        return new Patient(
                PersonalData.getDummyData(), record, prescriptions
        );
    }

    public PersonalData getEmergencyInfo() {
        return emergencyInfo;
    }

    public List<MedicalRecordEntry> getMedicalRecord() {
        if (medicalRecord == null) {
            return new ArrayList<>();
        }
        return medicalRecord;
    }

    public List<Prescription> getPrescriptions() {
        if (prescriptions == null) {
            return new ArrayList<>();
        }
        return prescriptions;
    }

    public void addMedicalRecordEntry(MedicalRecordEntry newEntry) {
        getMedicalRecord().add(newEntry);
        getPrescriptions().add(newEntry.getPrescription());
    }
}
