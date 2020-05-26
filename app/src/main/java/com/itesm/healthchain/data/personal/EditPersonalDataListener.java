package com.itesm.healthchain.data.personal;

import com.itesm.healthchain.data.model.PersonalData;

public interface EditPersonalDataListener {
    public void onEditFailure();
    public void onEditSuccess(PersonalData editedData);
}
