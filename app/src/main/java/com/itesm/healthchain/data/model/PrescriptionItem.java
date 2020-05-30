package com.itesm.healthchain.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PrescriptionItem implements Parcelable {
    private String name;
    private String dose;
    private String date;

    public PrescriptionItem(String name, String dose, String date) {
        this.name = name;
        this.dose = dose;
        this.date = date;
    }

    public static PrescriptionItem createDummyItem(){
        return new PrescriptionItem("Redoxon", "Una tableta", "25/04/2020");
    }

    protected PrescriptionItem(Parcel in) {
        name = in.readString();
        dose = in.readString();
        date = in.readString();
    }

    public static final Creator<PrescriptionItem> CREATOR = new Creator<PrescriptionItem>() {
        @Override
        public PrescriptionItem createFromParcel(Parcel in) {
            return new PrescriptionItem(in);
        }

        @Override
        public PrescriptionItem[] newArray(int size) {
            return new PrescriptionItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDose() {
        return dose;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(dose);
        parcel.writeString(date);
    }
}
