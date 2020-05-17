package com.itesm.healthchain.ui.personal_data;

import com.itesm.healthchain.data.model.PersonalData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PersonalDataViewModel extends ViewModel {
    private MutableLiveData<PersonalData> mData;

    PersonalDataViewModel() {
        mData = new MutableLiveData<>();
    }

    public LiveData<PersonalData> getData() {
        return mData;
    }

    public void setData(PersonalData mData) {
        this.mData.setValue(mData);
    }
}