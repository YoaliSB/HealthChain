package com.itesm.healthchain.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.data.model.Doctor;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorAdapter extends GenericAdapter {

    public DoctorAdapter(List items) {
        super(items);
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
        return new DoctorAdapterViewHolder(view);
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder viewHolder, Object val) {
        Doctor doctor = (Doctor) val;

        DoctorAdapterViewHolder doctorAdapterViewHolder = (DoctorAdapterViewHolder) viewHolder;
        doctorAdapterViewHolder.name.setText(doctor.getName());
        doctorAdapterViewHolder.desc.setText(doctor.getDesc());
    }

    class DoctorAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView desc;

        public DoctorAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            desc = itemView.findViewById(R.id.text_desc);
        }
    }
}
