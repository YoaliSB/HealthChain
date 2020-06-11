package com.itesm.healthchain.ui.personal_data;

import android.content.Context;

import com.itesm.healthchain.data.model.Patient;
import com.itesm.healthchain.data.personal.PatientDataRepository;
import com.itesm.healthchain.data.model.PersonalData;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PatientDataViewModel extends PersonalDataViewModel {
    PatientDataRepository repository;
    LiveData<PersonalData> data;

    public PatientDataViewModel(Context context) {
        super();
        this.repository = PatientDataRepository.getInstance(context);
    }

    @Override
    public LiveData<PersonalData> getData() {
        data =  Transformations.map(repository.fetchPatient(), new Function<Patient, PersonalData>() {
            @Override
            public PersonalData apply(Patient input) {
                return input.getEmergencyInfo();
            }
        });
        return data;
    }

    public LiveData<PersonalData>getCurrentData() {
        return data;
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final Context context;

        public Factory(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PatientDataViewModel(context);
        }
    }
}
