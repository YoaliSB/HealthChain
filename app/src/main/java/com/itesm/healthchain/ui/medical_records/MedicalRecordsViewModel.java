package com.itesm.healthchain.ui.medical_records;

import com.itesm.healthchain.data.model.MedicalRecordEntry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MedicalRecordsViewModel extends ViewModel {
    private MutableLiveData<MedicalRecordEntry> mData;

    public MedicalRecordsViewModel() {
        MedicalRecordEntry data = MedicalRecordEntry.createDummyEntry();
        mData = new MutableLiveData<>();
        mData.setValue(data);
    }

    public LiveData<MedicalRecordEntry> getData() {
        return mData;
    }
}
