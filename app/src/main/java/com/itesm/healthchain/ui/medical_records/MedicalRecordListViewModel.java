package com.itesm.healthchain.ui.medical_records;

import android.content.Context;

import com.itesm.healthchain.data.model.Patient;
import com.itesm.healthchain.data.personal.PatientDataRepository;
import com.itesm.healthchain.data.model.MedicalRecordEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MedicalRecordListViewModel extends ViewModel {
    private PatientDataRepository repository;


    public MedicalRecordListViewModel(Context context) {
        this.repository = PatientDataRepository.getInstance(context);
    }

    public LiveData<List<MedicalRecordEntry>> getData() {
        return Transformations.map(repository.subscribeForPatient(), new Function<Patient, List<MedicalRecordEntry>>() {
            @Override
            public List<MedicalRecordEntry> apply(Patient input) {
                return input.getMedicalRecord();
            }
        });
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final Context context;

        public Factory(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MedicalRecordListViewModel(context);
        }
    }

}