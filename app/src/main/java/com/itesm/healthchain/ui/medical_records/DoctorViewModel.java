package com.itesm.healthchain.ui.medical_records;

import android.content.Context;

import com.itesm.healthchain.data.DoctorRepository;
import com.itesm.healthchain.data.model.Doctor;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DoctorViewModel extends ViewModel {
    private DoctorRepository repository;
    private MutableLiveData<ArrayList<Doctor>> doctorLiveData;

    public DoctorViewModel(Context context) {
        doctorLiveData = new MutableLiveData<>();
        this.repository = DoctorRepository.getInstance(context);
    }

    public LiveData<ArrayList<Doctor>> getData() {
        return repository.fetchDoctors();
    }

    public void deleteDoctor(Doctor deletedDoctor) {
        repository.deleteDoctor(deletedDoctor);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final Context context;

        public Factory(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new DoctorViewModel(context);
        }
    }

}