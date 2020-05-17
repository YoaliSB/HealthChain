package com.itesm.healthchain.ui.personal_data;

import android.content.Context;

import com.itesm.healthchain.data.PersonalDataRepository;
import com.itesm.healthchain.data.model.PersonalData;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PatientDataViewModel extends PersonalDataViewModel {
    PersonalDataRepository repository;

    public PatientDataViewModel(Context context) {
        super();
        this.repository = PersonalDataRepository.getInstance(context);
    }

    @Override
    public LiveData<PersonalData> getData() {
        mData = repository.fetchPersonalData();
        return mData;
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
