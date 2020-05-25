package com.itesm.healthchain.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.data.model.PatientInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PatientAdapter extends GenericAdapter {

    public PatientAdapter(List items) {
        super(items);
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item, parent, false);
        return new PatientAdapterViewHolder(view);
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder viewHolder, Object val) {
        PatientInfo patient = (PatientInfo) val;

        PatientAdapterViewHolder patientAdapterViewHolder = (PatientAdapterViewHolder) viewHolder;
        patientAdapterViewHolder.name.setText(patient.getName());
    }

    class PatientAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public PatientAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
        }
    }
}
