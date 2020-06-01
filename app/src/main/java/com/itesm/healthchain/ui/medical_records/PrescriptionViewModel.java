package com.itesm.healthchain.ui.medical_records;

import com.itesm.healthchain.data.model.Prescription;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PrescriptionViewModel extends ViewModel {

    MutableLiveData<ArrayList<Prescription>> prescriptionLiveData;
    ArrayList<Prescription> prescriptionArrayList;

    public PrescriptionViewModel() {
        prescriptionLiveData = new MutableLiveData<>();

        // call your Rest API in init method
        init();
    }

    public MutableLiveData<ArrayList<Prescription>> getPrescriptionMutableLiveData() {
        return prescriptionLiveData;
    }

    public void init(){
        populateList();
        prescriptionLiveData.setValue(prescriptionArrayList);
    }

    public void populateList(){
        Prescription prescription = Prescription.createDummyData();
        prescriptionArrayList = new ArrayList<>();
        prescriptionArrayList.add(prescription);
        prescriptionArrayList.add(prescription);
        prescriptionArrayList.add(prescription);
    }
}