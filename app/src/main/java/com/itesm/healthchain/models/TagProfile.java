package com.itesm.healthchain.models;

import java.io.Serializable;

public class TagProfile implements Serializable {

    private String name;
    private String birthDate;
    private String bloodType;
    private String weight;
    private String height;
    private String hospital;
    private String ailments;
    private String allergies;
    private String id;
    private String contactName;
    private String contactPhone;
    private String contactRelationship;

    public TagProfile(){

        //Testing
//        this.id = "1";
//        this.name = "Brandon Triplett";
//        this.birthDate = "27/03/1985";
//        this.bloodType = "B+";
//        this.weight = "75";
//        this.height = "170";
//        this.contactName = "Elena Rodríguez Blanco";
//        this.contactPhone = "5588996622";
//        this.contactRelationship = "Madre";
//        this.hospital = "Hospital Español";
//        this.ailments = "Diabetes tipo 1, fumador";
//        this.allergies = "Ninguna";

        this.id = "";
        this.name = "";
        this.birthDate = "";
        this.bloodType = "";
        this.weight = "";
        this.height = "";
        this.hospital = "";
        this.ailments = "";
        this.allergies = "";
        this.contactName = "";
        this.contactPhone = "";
        this.contactRelationship = "";
    }

    public TagProfile(String[] records){
        this.id = records[0];
        this.name = records[1];
        this.birthDate = records[2];
        this.bloodType = records[3];
        this.weight = records[4];
        this.height = records[5];
        this.hospital = records[6];
        this.ailments = records[7];
        this.allergies = records[8];
        this.contactName = records[9];
        this.contactPhone = records[10];
        this.contactRelationship = records[11];
    }

    public TagProfile(String name, String birthDate, String bloodType, String weight, String height,
                      String hospital, String ailments, String allergies, String contactName,
                      String contactPhone, String contactRelationship) {
        this.id = "00001";
        this.name = name;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.weight = weight;
        this.height = height;
        this.hospital = hospital;
        this.ailments = ailments;
        this.allergies = allergies;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactRelationship = contactRelationship;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getHospital() {
        return hospital;
    }

    public String getAilments() {
        return ailments;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getId() {
        return id;
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
}
