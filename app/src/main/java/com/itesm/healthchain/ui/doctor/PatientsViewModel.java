package com.itesm.healthchain.ui.doctor;

import com.itesm.healthchain.data.doctors.DoctorRepository;
import com.itesm.healthchain.data.model.Doctor;
import com.itesm.healthchain.data.model.PatientInfo;
import com.itesm.healthchain.data.patients.PatientRepository;
import com.itesm.healthchain.ui.medical_records.DoctorViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PatientsViewModel extends ViewModel {
    private PatientRepository repository;
    private MutableLiveData<ArrayList<PatientInfo>> patientLiveData;

    public PatientsViewModel(PatientRepository repository) {
        patientLiveData = new MutableLiveData<>();
        this.repository = repository;
    }

    public LiveData<ArrayList<PatientInfo>> getData() {
        return repository.fetchPatients();
    }

    public LiveData<ArrayList<PatientInfo>> getDummyData() {
        ArrayList<PatientInfo> patients = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            patients.add(new PatientInfo("Patient " + i, "patient" + i + "mail.com"));
        }
        MutableLiveData<ArrayList<PatientInfo>> data = new MutableLiveData<ArrayList<PatientInfo>>();
        data.setValue(patients);
        return data;
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final PatientRepository repository;

        public Factory(PatientRepository repository) {
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PatientsViewModel(repository);
        }
    }
}