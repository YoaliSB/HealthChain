package com.itesm.healthchain.ui.medical_records;

import android.content.Context;

import com.itesm.healthchain.data.model.Patient;
import com.itesm.healthchain.data.model.Prescription;
import com.itesm.healthchain.data.personal.PatientDataRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PrescriptionViewModel extends ViewModel {
    private PatientDataRepository repository;


    public PrescriptionViewModel(Context context) {
        this.repository = PatientDataRepository.getInstance(context);
    }

    public LiveData<List<Prescription>> getData() {
        return Transformations.map(repository.subscribeForPatient(), new Function<Patient, List<Prescription>>() {
            @Override
            public List<Prescription> apply(Patient input) {
                return input.getPrescriptions();
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
            return (T) new PrescriptionViewModel(context);
        }
    }

}