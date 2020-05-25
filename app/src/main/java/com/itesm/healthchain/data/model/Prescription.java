package com.itesm.healthchain.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Prescription implements Parcelable {
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

    protected Prescription(Parcel in) {
        doctor = in.readString();
        date = in.readString();
        items = in.createTypedArrayList(PrescriptionItem.CREATOR);
    }

    public static final Creator<Prescription> CREATOR = new Creator<Prescription>() {
        @Override
        public Prescription createFromParcel(Parcel in) {
            return new Prescription(in);
        }

        @Override
        public Prescription[] newArray(int size) {
            return new Prescription[size];
        }
    };

    public String getDoctor() {
        return doctor;
    }

    public String getDate() {
        return date;
    }

    public List<PrescriptionItem> getItems() {
        return items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(doctor);
        parcel.writeString(date);
        parcel.writeList(items);
    }
}
