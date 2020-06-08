package com.itesm.healthchain.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PatientInfo implements Parcelable {
    private String name;
    private String email;

    public PatientInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    protected PatientInfo(Parcel in) {
        name = in.readString();
        email = in.readString();
    }

    public static final Creator<PatientInfo> CREATOR = new Creator<PatientInfo>() {
        @Override
        public PatientInfo createFromParcel(Parcel in) {
            return new PatientInfo(in);
        }

        @Override
        public PatientInfo[] newArray(int size) {
            return new PatientInfo[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
    }
}
