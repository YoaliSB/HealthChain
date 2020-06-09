package com.itesm.healthchain.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.data.model.Doctor;
import com.itesm.healthchain.data.doctors.DoctorDeleteListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorAdapter extends GenericAdapter<Doctor> {

    private DoctorDeleteListener deleteListener;
    public DoctorAdapter(List<Doctor> items) {
        super(items);
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
        return new DoctorAdapterViewHolder(view);
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder viewHolder, final Doctor doctor) {
        DoctorAdapterViewHolder doctorAdapterViewHolder = (DoctorAdapterViewHolder) viewHolder;
        doctorAdapterViewHolder.name.setText(doctor.getName());
        doctorAdapterViewHolder.desc.setText(doctor.getDesc());
        doctorAdapterViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteListener.onDelete(doctor);
            }
        });
    }

    public void setDeleteListener(DoctorDeleteListener listener) {
        this.deleteListener = listener;
    }

    class DoctorAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView desc;
        View deleteButton;

        public DoctorAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            desc = itemView.findViewById(R.id.text_desc);
            deleteButton = itemView.findViewById(R.id.delete_btn);
        }
    }
}
