package com.itesm.healthchain.ui.medical_records;

import android.content.Context;

import com.itesm.healthchain.data.personal.PersonalDataRepository;
import com.itesm.healthchain.data.model.MedicalRecordEntry;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MedicalRecordListViewModel extends ViewModel {
    private PersonalDataRepository repository;
    private MutableLiveData<List<MedicalRecordEntry>> liveData;

    public MedicalRecordListViewModel(Context context) {
        liveData = new MutableLiveData<>();
        this.repository = PersonalDataRepository.getInstance(context);
    }

    public LiveData<List<MedicalRecordEntry>> getData() {
        // TODO: actually get data from repository
        ArrayList<MedicalRecordEntry> list = new ArrayList();
        list.add(new MedicalRecordEntry());
        liveData.setValue(list);
        return liveData;
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