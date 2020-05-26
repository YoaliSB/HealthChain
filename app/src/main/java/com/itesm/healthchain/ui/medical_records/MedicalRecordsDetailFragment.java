package com.itesm.healthchain.ui.medical_records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.adapters.PrescriptionItemAdapter;
import com.itesm.healthchain.data.model.MedicalRecordEntry;
import com.itesm.healthchain.data.model.PrescriptionItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MedicalRecordsDetailFragment extends Fragment {

    private PrescriptionItemAdapter prescriptionItemAdapter;
    private List<PrescriptionItem> prescriptions;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.medical_records_fragment, container, false);

        final TextView doctor = root.findViewById(R.id.text_doctor);
        final TextView name = root.findViewById(R.id.text_name);
        final TextView sex = root.findViewById(R.id.text_sex);
        final TextView ta = root.findViewById(R.id.text_ta);
        final TextView fc = root.findViewById(R.id.text_fc);
        final TextView fr = root.findViewById(R.id.text_fr);
        final TextView temp = root.findViewById(R.id.text_temp);
        final TextView weight = root.findViewById(R.id.text_weight);
        final TextView height = root.findViewById(R.id.text_height);
        final TextView imc = root.findViewById(R.id.text_bmi);
        final TextView observations = root.findViewById(R.id.text_observations);
        final TextView diagnosis = root.findViewById(R.id.text_diagnosis);
        final RecyclerView prescriptionsList = root.findViewById(R.id.list_prescriptions);
        prescriptionItemAdapter = new PrescriptionItemAdapter();
        prescriptionsList.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();

        if (bundle != null) {
            MedicalRecordEntry entry = bundle.getParcelable("medicalRecord");
            name.setText(entry.getName());
            doctor.setText(entry.getDoctor());
            sex.setText(entry.getSex());
            ta.setText(entry.getTa());
            fc.setText(entry.getFc());
            fr.setText(entry.getFr());
            temp.setText(entry.getTemp());
            weight.setText(entry.getWeight());
            height.setText(entry.getHeight());
            imc.setText(entry.getImc());
            observations.setText(entry.getObservations());
            diagnosis.setText(entry.getDiagnostic());
            prescriptions = entry.getPrescription().getItems();
            prescriptionItemAdapter.setPrescriptionItems(prescriptions);
            prescriptionsList.setAdapter(prescriptionItemAdapter);
        }

        return root;
    }


}
