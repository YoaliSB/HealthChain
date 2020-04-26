package com.itesm.healthchain.models;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PersonalData {

    private String id;
    private String name;
    private String birthDate;
    private String blood;
    private double weight;
    private double height;
    private String contactName;
    private String contactPhone;
    private String contactRelationship;
    private String hospital;
    private List<String> ailments;
    private List<String> allergies;

    public PersonalData(String id, String name, String birthDate, String blood, int weight, int height,
                        String contactName, String contactPhone, String contactRelationship,
                        String hospital, List<String> ailments, List<String> allergies) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
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
        this.id = "1";
        this.name = "Juanito Pérez Rodríguez";
        this.birthDate = "27/03/1985";
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

    public PersonalData(TagProfile tagProfile){
        this.id = tagProfile.getId();
        this.name = tagProfile.getName();
        this.birthDate = tagProfile.getBirthDate();
        this.weight = Double.parseDouble(tagProfile.getWeight());
        this.height = Double.parseDouble(tagProfile.getHeight());
        this.hospital = tagProfile.getHospital();
        this.ailments = getList(tagProfile.getAilments());
        this.allergies = getList(tagProfile.getAllergies());
        this.contactName = tagProfile.getContactName();
        this.contactPhone = tagProfile.getContactPhone();
        this.contactRelationship = tagProfile.getContactRelationship();
    }

    private List<String> getList(String str) {
        String array[] = str.split(",");
        List<String> al = Arrays.asList(array);
        return al;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        StringBuilder sb = new StringBuilder();
        sb.append(birthDate);
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
