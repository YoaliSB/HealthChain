package com.itesm.healthchain.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.data.model.MedicalRecordEntry;
import com.itesm.healthchain.ui.medical_records.MedicalRecordEntrySelectListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

public class MedicalRecordAdapter extends GenericAdapter {

    private MedicalRecordEntrySelectListener selectListener;

    public MedicalRecordAdapter(List items) {
        super(items);
    }

    public void setSelectListener(MedicalRecordEntrySelectListener selectListener) {
        this.selectListener = selectListener;
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_title, parent, false);
        return new MedicalRecordAdapterViewHolder(view);
    }

    @Override
    public void onBindData(final RecyclerView.ViewHolder viewHolder, Object val) {
        final MedicalRecordEntry entry = (MedicalRecordEntry) val;
        MedicalRecordAdapterViewHolder medicalRecordAdapterViewHolder = (MedicalRecordAdapterViewHolder) viewHolder;
        medicalRecordAdapterViewHolder.doctor.setText(entry.getDoctor());
        medicalRecordAdapterViewHolder.date.setText(entry.getDate());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectListener.onSelect(entry);
            }
        });
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
