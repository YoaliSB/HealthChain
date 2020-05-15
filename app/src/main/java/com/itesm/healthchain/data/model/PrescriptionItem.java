package com.itesm.healthchain.data.model;

public class PrescriptionItem {
    private String name;
    private String dose;
    private String date;

    public PrescriptionItem(String name, String dose, String date) {
        this.name = name;
        this.dose = dose;
        this.date = date;
    }

    public PrescriptionItem(){
        this.name = "Redoxon";
        this.dose = "Una tableta";
        this.date = "25/04/2020";
    }

    public String getName() {
        return name;
    }

    public String getDose() {
        return dose;
    }

    public String getDate() {
        return date;
    }
}
