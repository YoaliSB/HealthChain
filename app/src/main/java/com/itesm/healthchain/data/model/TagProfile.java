package com.itesm.healthchain.data.model;

import android.nfc.Tag;

import org.json.JSONException;
import org.json.JSONObject;

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
    private String email;
    private String contactName;
    private String contactPhone;
    private String contactRelationship;

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setContactRelationship(String contactRelationship) {
        this.contactRelationship = contactRelationship;
    }

    public TagProfile(){

        this.email = "";
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

    public TagProfile(String email, String name, String birthDate, String bloodType, String weight, String height,
                      String hospital, String ailments, String allergies,
                      String contactName, String contactPhone, String contactRelationship) {
        this.email = email;
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

    public TagProfile(PersonalData data){
        this.email = data.getEmail();
        this.name = data.getName();
        this.birthDate = data.getBirthDate();
        this.bloodType = data.getBlood();
        this.weight = data.getWeight();
        this.height = data.getHeight();
        this.hospital = data.getHospital();
        this.ailments = data.getAilments();
        this.allergies = data.getAllergies();
        this.contactName = data.getContactName();
        this.contactPhone = data.getContactPhone();
        this.contactRelationship = data.getContactRelationship();
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("birthDate", birthDate);
        json.put("bloodType", bloodType);
        json.put("weight", weight);
        json.put("height", height);
        json.put("hospital", hospital);
        json.put("ailments", ailments);
        json.put("allergies", allergies);
        json.put("contactName", contactName);
        json.put("contactPhone", contactPhone);
        json.put("contactRelationship", contactRelationship);
        return json;
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

    public String getEmail() {
        return email;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactRelationship() { return contactRelationship; }
}
