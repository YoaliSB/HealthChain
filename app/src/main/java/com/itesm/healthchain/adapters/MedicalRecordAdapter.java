package com.itesm.healthchain.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.data.model.MedicalRecordEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MedicalRecordAdapter extends GenericAdapter {

    public MedicalRecordAdapter(List items) {
        super(items);
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_title, parent, false);
        return new MedicalRecordAdapterViewHolder(view);
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder viewHolder, Object val) {
        MedicalRecordEntry entry = (MedicalRecordEntry) val;
        MedicalRecordAdapterViewHolder medicalRecordAdapterViewHolder = (MedicalRecordAdapterViewHolder) viewHolder;
        medicalRecordAdapterViewHolder.doctor.setText(entry.getDoctor());
        medicalRecordAdapterViewHolder.date.setText(entry.getDate());
    }

    class MedicalRecordAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView doctor;
        TextView date;

        public MedicalRecordAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor = itemView.findViewById(R.id.text_doctor);
            date = itemView.findViewById(R.id.text_date);
        }
    }
}
