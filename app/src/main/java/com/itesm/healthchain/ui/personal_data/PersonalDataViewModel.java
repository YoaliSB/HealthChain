package com.itesm.healthchain.ui.personal_data;

import com.itesm.healthchain.models.PersonalData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PersonalDataViewModel extends ViewModel {

    private MutableLiveData<PersonalData> mData;

    public PersonalDataViewModel() {
        PersonalData data = new PersonalData();
        mData = new MutableLiveData<>();
        mData.setValue(data);
    }

    public LiveData<PersonalData> getData() {
        return mData;
    }

    public void setData(PersonalData mData) {
        this.mData.setValue(mData);
    }
}