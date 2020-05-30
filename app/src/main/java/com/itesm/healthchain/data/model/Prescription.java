package com.itesm.healthchain.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
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

    public static Prescription createDummyData(){
        ArrayList<PrescriptionItem> items = new ArrayList<>();
        items.add(PrescriptionItem.createDummyItem());
        items.add(PrescriptionItem.createDummyItem());
        return new Prescription("Rogelio Flores Puerta", "05/05/2020", items);
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

    public static class PrescriptionTypeConverter implements JsonSerializer<Prescription>,
            JsonDeserializer<Prescription> {

        Gson gson = new Gson();

        @Override
        public JsonElement serialize(Prescription src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeToString(gson.toJson(src)
                    .getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP));
        }

        @Override
        public Prescription deserialize(JsonElement json,
                                        Type typeOfT,
                                        JsonDeserializationContext context) throws JsonParseException {
            return gson.fromJson(new String(Base64.decode(json.getAsString(),
                    Base64.NO_WRAP),
                    StandardCharsets.UTF_8), Prescription.class);
        }
    }

}
