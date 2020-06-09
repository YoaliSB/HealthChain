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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MedicalRecordEntry implements Parcelable {
    private String name;
    private String doctor;
    private String date;
    private String sex;
    private String ta;
    private String fc;
    private String fr;
    private double temp;
    private double weight;
    private double height;
    private double imc;
    private String observations;
    private String diagnostic;
    private Prescription prescription;

    public MedicalRecordEntry(String name, String doctor, String date, String sex, String ta, String fc,
                              String fr, double temp, double weight, double height, String observations,
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

    public static MedicalRecordEntry createDummyEntry() {
        // TODO: erase this
        String name = "Juanito Perez";
        String doctor = "Rogelio Flores Puerta";
        String date = "05/05/2020";
        String sex = "Masculino";
        String  ta = "120/80";
        String fc = "80";
        String fr = "16";
        double temp = 36.5;
        double  weight = 75;
        double  height = 172;
        String  observations = "Sin fiebre";
        String  diagnostic = "Paciente presenta cuadro de resfriado común. Descanso por 3 días y mantenerse hidratado";
        Prescription prescription = Prescription.createDummyData();
        return new MedicalRecordEntry(name, doctor, date, sex, ta, fc, fr, temp, weight, height,
                observations, diagnostic, prescription);
    }

    public static MedicalRecordEntry prefillEntry(PersonalData data, String doctorName) {
        return new MedicalRecordEntry(data.getName(),
                doctorName,
                new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()),
                "",
                "",
                "",
                "",
                36.5,
                Double.parseDouble(data.getWeight()),
                Double.parseDouble(data.getHeight()),
                "",
                "",
                null);
    }

    protected MedicalRecordEntry(Parcel in) {
        name = in.readString();
        doctor = in.readString();
        date = in.readString();
        sex = in.readString();
        ta = in.readString();
        fc = in.readString();
        fr = in.readString();
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
        return temp + "";
    }

    public String getWeight() {
        return weight + "";
    }

    public String getHeight() {
        return height + "";
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
        parcel.writeString(fc);
        parcel.writeString(fr);
        parcel.writeDouble(temp);
        parcel.writeDouble(weight);
        parcel.writeDouble(height);
        parcel.writeDouble(imc);
        parcel.writeString(observations);
        parcel.writeString(diagnostic);
        parcel.writeParcelable(prescription, i);

    }

    public static class MedicalRecordEntryTypeConverter implements JsonSerializer<MedicalRecordEntry>,
            JsonDeserializer<MedicalRecordEntry> {

        Gson gson = new Gson();

        @Override
        public JsonElement serialize(MedicalRecordEntry src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeToString(gson.toJson(src)
                    .getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP));
        }

        @Override
        public MedicalRecordEntry deserialize(JsonElement json,
                                              Type typeOfT,
                                              JsonDeserializationContext context) throws JsonParseException {
            return gson.fromJson(new String(Base64.decode(json.getAsString(),
                    Base64.NO_WRAP),
                    StandardCharsets.UTF_8), MedicalRecordEntry.class);
        }
    }
}
