package com.itesm.healthchain.data;

import android.content.Context;

import com.itesm.healthchain.data.model.PersonalData;

import androidx.lifecycle.MutableLiveData;

public class PersonalDataRepository {
    private static volatile PersonalDataRepository instance;
    private PersonalDataNetworkDataSource personalDataNetworkDataSource;

    // private constructor : singleton access
    private PersonalDataRepository(final Context context) {
        personalDataNetworkDataSource = new PersonalDataNetworkDataSource(context);
    }

    public static PersonalDataRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PersonalDataRepository(context);
        }
        return instance;
    }

    public MutableLiveData<PersonalData> fetchPersonalData(){
        personalDataNetworkDataSource.fetchPersonalData();
        return personalDataNetworkDataSource.getPersonalData();
    }
}
