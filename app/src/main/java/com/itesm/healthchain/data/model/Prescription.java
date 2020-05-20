package com.itesm.healthchain.data.model;

import java.util.ArrayList;
import java.util.List;

public class Prescription {
    private String doctor;
    private String date;
    private List<PrescriptionItem> items;

    public Prescription(String doctor, String date, List<PrescriptionItem> items) {
        this.doctor = doctor;
        this.date = date;
        this.items = items;
    }

    public Prescription(){
        // TODO: erase this
        this.doctor = "Azucena Sotelo";
        this.date = "20/04/2020";
        this.items = new ArrayList<>();
        items.add(new PrescriptionItem());
        items.add(new PrescriptionItem());
    }

    public String getDoctor() {
        return doctor;
    }

    public String getDate() {
        return date;
    }

    public List<PrescriptionItem> getItems() {
        return items;
    }
}
