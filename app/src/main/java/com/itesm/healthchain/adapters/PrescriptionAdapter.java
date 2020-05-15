package com.itesm.healthchain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.data.model.Prescription;
import com.itesm.healthchain.data.model.PrescriptionItem;

import java.util.List;

public class PrescriptionAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Prescription> listDataHeader;

    public PrescriptionAdapter(Context context, List<Prescription> listDataHeader) {
        this.context = context;
        this.listDataHeader = listDataHeader;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataHeader.get(groupPosition).getItems().size();
    }

    @Override
    public Prescription getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public PrescriptionItem getChild(int groupPosition, int childPosition) {
        return listDataHeader.get(groupPosition).getItems().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Prescription currentPrescription = getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater inflater =(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.prescription_title,null);
        }
        TextView doctor = convertView.findViewById(R.id.text_doctor);
        doctor.setText(currentPrescription.getDoctor());

        TextView date = convertView.findViewById(R.id.text_date);
        date.setText(currentPrescription.getDate());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final PrescriptionItem prescriptionItem = getChild(groupPosition, childPosition);
        if(convertView == null) {
            LayoutInflater inflater =(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.prescription_item, null);
        }

        TextView name = convertView.findViewById(R.id.text_name);
        name.setText(prescriptionItem.getName());

        TextView dose = convertView.findViewById(R.id.text_dose);
        dose.setText(prescriptionItem.getDose());

        TextView date = convertView.findViewById(R.id.text_date);
        date.setText(prescriptionItem.getDate());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

