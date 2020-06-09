package com.itesm.healthchain.data.personal;

public interface EditMedicalRecordListener {
    public void onEditRecordFailure();
    public void onEditRecordSuccess(String email);
}
