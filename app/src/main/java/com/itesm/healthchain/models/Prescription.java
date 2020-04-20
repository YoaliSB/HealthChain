package com.itesm.healthchain.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Prescription {
    private String name;
    private String dose;
    private String date;

    public Prescription(String name, String dose, String date) {
        this.name = name;
        this.dose = dose;
        this.date = date;
    }

    public Prescription(){
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
