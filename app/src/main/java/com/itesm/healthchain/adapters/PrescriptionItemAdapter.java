package com.itesm.healthchain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.models.PrescriptionItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PrescriptionItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PrescriptionItem> prescriptionItems;
    private Context context;

    public PrescriptionItemAdapter(Context context, List<PrescriptionItem> prescriptionItems) {
        this.context = context;
        this.prescriptionItems = prescriptionItems;
    }

    // TODO: Remove context dependency, add setter for items, remove items from constructor (whole constructor)
    // diff in Line

    @NonNull
    @Override
    public PrescriptionItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.prescription_item, parent, false);

        return new PrescriptionItemAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PrescriptionItem prescriptionItem = prescriptionItems.get(position);
        PrescriptionItemAdapterViewHolder viewHolder = (PrescriptionItemAdapterViewHolder) holder;

        viewHolder.name.setText(prescriptionItem.getName());
        viewHolder.dose.setText(prescriptionItem.getDose());
        viewHolder.date.setText(prescriptionItem.getDate());
    }

    @Override
    public int getItemCount() {
        return prescriptionItems.size();
    }

    class PrescriptionItemAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView dose;
        TextView date;

        public PrescriptionItemAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            dose = itemView.findViewById(R.id.text_dose);
            date = itemView.findViewById(R.id.text_date);
        }
    }
}
