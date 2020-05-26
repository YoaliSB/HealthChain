package com.itesm.healthchain.data.doctors;

import com.itesm.healthchain.data.model.Doctor;

public interface DoctorDeleteListener {
    public void onDelete(Doctor deletedDoctor);
    public void onFailure();
}
