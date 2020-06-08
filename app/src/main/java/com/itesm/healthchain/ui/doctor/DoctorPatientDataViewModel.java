package com.itesm.healthchain.ui.doctor;

import android.content.Context;

import com.itesm.healthchain.data.model.PersonalData;
import com.itesm.healthchain.data.personal.PersonalDataRepository;
import com.itesm.healthchain.ui.personal_data.PersonalDataViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DoctorPatientDataViewModel extends PersonalDataViewModel {
    PersonalDataRepository repository;
    String email;

    public DoctorPatientDataViewModel(Context context) {
        super();
        this.repository = PersonalDataRepository.getInstance(context);
    }

    public LiveData<PersonalData> getData() {
        return repository.fetchPersonalDataForDoctor(email);
    }

    public void setEmail(String email) {
        this.email = email;
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
