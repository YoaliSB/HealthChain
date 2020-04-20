package com.itesm.healthchain.ui.medical_records;

import com.itesm.healthchain.models.MedicalRecord;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MedicalRecordsViewModel extends ViewModel {
    private MutableLiveData<MedicalRecord> mData;

    public MedicalRecordsViewModel() {
        MedicalRecord data = new MedicalRecord();
        mData = new MutableLiveData<>();
        mData.setValue(data);
    }

    public LiveData<MedicalRecord> getData() {
        return mData;
    }
}
