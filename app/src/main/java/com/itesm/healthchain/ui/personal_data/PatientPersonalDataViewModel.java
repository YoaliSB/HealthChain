package com.itesm.healthchain.ui.personal_data;

import com.itesm.healthchain.data.PersonalDataRepository;
import com.itesm.healthchain.data.model.PersonalData;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PatientPersonalDataViewModel extends PersonalDataViewModel {
    PersonalDataRepository repository;

    public PatientPersonalDataViewModel(PersonalDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public LiveData<PersonalData> getData() {
        return repository.fetchPersonalData();
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final PersonalDataRepository dataRepository;

        public Factory(PersonalDataRepository repository) {
            this.dataRepository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PatientPersonalDataViewModel(dataRepository);
        }
    }
}
