package com.itesm.healthchain.data.model;

public class PatientInfo {
    private String name;
    private String email;

    public PatientInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
