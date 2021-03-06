package com.itesm.healthchain.ui.doctor;

import android.content.Context;

import com.itesm.healthchain.data.model.MedicalRecordEntry;
import com.itesm.healthchain.data.model.Patient;
import com.itesm.healthchain.data.model.PersonalData;
import com.itesm.healthchain.data.personal.PatientDataRepository;
import com.itesm.healthchain.ui.personal_data.PersonalDataViewModel;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DoctorPatientDataViewModel extends PersonalDataViewModel {
    PatientDataRepository repository;
    String email = "";

    public DoctorPatientDataViewModel(Context context) {
        super();
        this.repository = PatientDataRepository.getInstance(context);
    }

    @Override
    public LiveData<PersonalData> getData() {
        return Transformations.map(repository.subscribeForPatient(), new Function<Patient, PersonalData>() {
            @Override
            public PersonalData apply(Patient input) {
                return input.getEmergencyInfo();
            }
        });
    }

    public void fetchPersonalData(String email) {
        if(!this.email.equals(email)) {
            this.email = email;
            repository.fetchPersonalDataForDoctor(email);
        }
    }

    public void updateMedicalRecord(MedicalRecordEntry newEntry) {
        repository.updateMedicalRecord(email, newEntry);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final Context context;

        public Factory(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new DoctorPatientDataViewModel(context);
        }
    }
}
