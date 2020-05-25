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

    public DoctorViewModel(DoctorRepository repository) {
        doctorLiveData = new MutableLiveData<>();
        this.repository = repository;
    }

    public LiveData<ArrayList<Doctor>> getData() {
        return repository.fetchDoctors();
    }

    public LiveData<ArrayList<Doctor>> getDummyData() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            doctors.add(new Doctor(Integer.toString(i), "Doctor #" + i, "This is doctor #" + i, true));
        }
        MutableLiveData<ArrayList<Doctor>> data = new MutableLiveData<ArrayList<Doctor>>();
        data.setValue(doctors);
        return data;
    }

    public void deleteDoctor(Doctor deletedDoctor) {
        repository.deleteDoctor(deletedDoctor);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final DoctorRepository repository;

        public Factory(DoctorRepository repository) {
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new DoctorViewModel(repository);
        }
    }

}