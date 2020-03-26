package com.itesm.healthchain.models;

import java.util.LinkedList;
import java.util.List;

public class PersonalData {
    private String name;
    private int age;
    private String blood;
    private double weight;
    private double height;
    private String contactName;
    private String contactPhone;
    private String contactRelationship;
    private String hospital;
    private List<String> ailments;
    private List<String> allergies;

    public PersonalData(String name, int age, String blood, int weight, int height,
                        String contactName, String contactPhone, String contactRelationship,
                        String hospital, List<String> ailments, List<String> allergies) {
        this.name = name;
        this.age = age;
        this.blood = blood;
        this.weight = weight;
        this.height = height;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactRelationship = contactRelationship;
        this.hospital = hospital;
        this.ailments = ailments;
        this.allergies =allergies;
    }

    public PersonalData() {
        this.name = "Juanito Pérez Rodríguez";
        this.age = 27;
        this.blood = "A+";
        this.weight = 75;
        this.height = 170;
        this.contactName = "Elena Rodríguez Blanco";
        this.contactPhone = "5588996622";
        this.contactRelationship = "Madre";
        this.hospital = "Hospital Español";
        this.ailments = new LinkedList<>();
        this.allergies = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        StringBuilder sb = new StringBuilder();
        sb.append(age);
        sb.append(" años");
        return sb.toString();
    }

    public String getBlood() {
        return blood;
    }

    public String getWeight() {
        StringBuilder sb = new StringBuilder();
        sb.append(weight);
        sb.append(" kg");
        return sb.toString();
    }

    public String getHeight() {
        StringBuilder sb = new StringBuilder();
        sb.append(height);
        sb.append(" cm");
        return sb.toString();
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactRelationship() {
        return contactRelationship;
    }

    public String getHospital() {
        return hospital;
    }

    public List<String> getAilments() {
        return ailments;
    }

    public List<String> getAllergies() {
        return allergies;
    }
}
