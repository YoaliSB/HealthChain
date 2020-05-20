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

public class PersonAdapter extends GenericAdapter {

    public PersonAdapter(List items) {
        super(items);
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
        return new PersonAdapterViewHolder(view);
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder viewHolder, Object val) {
        Doctor doctor = (Doctor) val;

        PersonAdapterViewHolder personAdapterViewHolder = (PersonAdapterViewHolder) viewHolder;
        personAdapterViewHolder.name.setText(doctor.getName());
        personAdapterViewHolder.desc.setText(doctor.getDesc());
    }

    class PersonAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView desc;

        public PersonAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            desc = itemView.findViewById(R.id.text_desc);
        }
    }
}
