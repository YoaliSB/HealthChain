package com.itesm.healthchain.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;

public class MedicalRecordEntry implements Parcelable {
    private String doctor;
    private String name;
    private String date;
    private int age;
    private String sex;
    private String ta;
    private int fc;
    private int fr;
    private double temp;
    private double weight;
    private double height;
    private double imc;
    private String ailments;
    private String allergies;
    private String observations;
    private Prescription prescription;

    public MedicalRecordEntry(String doctor, String name, String date, int age, String sex, String ta, int fc,
                              int fr, double temp, double weight, double height, String observations,
                              String ailments, String allergies) {
        this.doctor = doctor;
        this.name = name;
        this.date = date;
        this.age = age;
        this.sex = sex;
        this.ta = ta;
        this.fc = fc;
        this.fr = fr;
        this.temp = temp;
        this.weight = weight;
        this.height = height;
        this.imc = calculateBMI();
        this.ailments = ailments;
        this.allergies = allergies;
        this.observations = observations;
    }

    public MedicalRecordEntry() {
        // TODO: erase this
        this.doctor = "Rogelio Flores Puerta";
        this.name = "Juanito Perez Rodriguez";
        this.date = "05/05/2020";
        this.age = 27;
        this.sex = "Masculino";
        this.ta = "120/80";
        this.fc = 80;
        this.fr = 16;
        this.temp = 36.5;
        this.weight = 75;
        this.height = 172;
        this.imc = calculateBMI();
        this.ailments = "Diabetes tipo 1, fumador";
        this.allergies = "Ninguna";
        this.observations = "Paciente presenta cuadro de resfriado común. Descanso por 3 días y mantenerse hidratado";
        this.prescription = new Prescription();
    }

    protected MedicalRecordEntry(Parcel in) {
        doctor = in.readString();
        name = in.readString();
        date = in.readString();
        age = in.readInt();
        sex = in.readString();
        ta = in.readString();
        fc = in.readInt();
        fr = in.readInt();
        temp = in.readDouble();
        weight = in.readDouble();
        height = in.readDouble();
        imc = in.readDouble();
        ailments = in.readString();
        allergies = in.readString();
        observations = in.readString();
        prescription = in.readParcelable(Prescription.class.getClassLoader());
    }

    public static final Creator<MedicalRecordEntry> CREATOR = new Creator<MedicalRecordEntry>() {
        @Override
        public MedicalRecordEntry createFromParcel(Parcel in) {
            return new MedicalRecordEntry(in);
        }

        @Override
        public MedicalRecordEntry[] newArray(int size) {
            return new MedicalRecordEntry[size];
        }
    };

    private double calculateBMI(){
        return this.weight/Math.pow(this.height/100, 2);
    }

    public String getDoctor() {
        return doctor;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getAge() {
        StringBuilder sb = new StringBuilder();
        sb.append(age);
        sb.append(" años");
        return sb.toString();
    }

    public String getSex() {
        return sex;
    }

    public String getTa() {
        return ta;
    }

    public String getFc() {
        return "" + fc;
    }

    public String getFr() {
        return "" + fr;
    }

    public String getTemp() {
        return temp + " C";
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

    public String getImc() {
        return "" + new DecimalFormat("0.00").format(imc);
    }

    public String getAilments() {
        return ailments;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getObservations() {
        return observations;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(doctor);
        parcel.writeString(name);
        parcel.writeString(date);
        parcel.writeInt(age);
        parcel.writeString(sex);
        parcel.writeString(ta);
        parcel.writeInt(fc);
        parcel.writeInt(fr);
        parcel.writeDouble(temp);
        parcel.writeDouble(weight);
        parcel.writeDouble(height);
        parcel.writeDouble(imc);
        parcel.writeString(ailments);
        parcel.writeString(allergies);
        parcel.writeString(observations);
        parcel.writeParcelable(prescription, i);

    }
}
