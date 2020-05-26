package com.itesm.healthchain.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;

public class MedicalRecordEntry implements Parcelable {
    private String name;
    private String doctor;
    private String date;
    private String sex;
    private String ta;
    private int fc;
    private int fr;
    private double temp;
    private double weight;
    private double height;
    private double imc;
    private String observations;
    private String diagnostic;
    private Prescription prescription;

    public MedicalRecordEntry(String name, String doctor, String date, String sex, String ta, int fc,
                              int fr, double temp, double weight, double height, String observations,
                              String diagnostic, Prescription prescription) {
        this.name = name;
        this.doctor = doctor;
        this.date = date;
        this.sex = sex;
        this.ta = ta;
        this.fc = fc;
        this.fr = fr;
        this.temp = temp;
        this.weight = weight;
        this.height = height;
        this.imc = calculateBMI();
        this.observations = observations;
        this.diagnostic = diagnostic;
        this.prescription = prescription;
    }

    public MedicalRecordEntry() {
        // TODO: erase this
        this.doctor = "Rogelio Flores Puerta";
        this.name = "Juanito Perez";
        this.date = "05/05/2020";
        this.sex = "Masculino";
        this.ta = "120/80";
        this.fc = 80;
        this.fr = 16;
        this.temp = 36.5;
        this.weight = 75;
        this.height = 172;
        this.imc = calculateBMI();
        this.observations = "Sin fiebre";
        this.diagnostic = "Paciente presenta cuadro de resfriado común. Descanso por 3 días y mantenerse hidratado";
        this.prescription = new Prescription();
    }

    protected MedicalRecordEntry(Parcel in) {
        name = in.readString();
        doctor = in.readString();
        date = in.readString();
        sex = in.readString();
        ta = in.readString();
        fc = in.readInt();
        fr = in.readInt();
        temp = in.readDouble();
        weight = in.readDouble();
        height = in.readDouble();
        imc = in.readDouble();
        observations = in.readString();
        diagnostic = in.readString();
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

    public String getName() {
        return name;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getDate() {
        return date;
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

    public String getObservations() {
        return observations;
    }

    public String getDiagnostic() {
        return diagnostic;
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
        parcel.writeString(name);
        parcel.writeString(doctor);
        parcel.writeString(date);
        parcel.writeString(sex);
        parcel.writeString(ta);
        parcel.writeInt(fc);
        parcel.writeInt(fr);
        parcel.writeDouble(temp);
        parcel.writeDouble(weight);
        parcel.writeDouble(height);
        parcel.writeDouble(imc);
        parcel.writeString(observations);
        parcel.writeString(diagnostic);
        parcel.writeParcelable(prescription, i);

    }
}
