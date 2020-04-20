package com.itesm.healthchain.ui.medical_records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.models.MedicalRecord;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MedicalRecordsFragment extends Fragment {

    private MedicalRecordsViewModel medicalRecordsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        medicalRecordsViewModel =
                ViewModelProviders.of(this).get(MedicalRecordsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_medical_records, container, false);

        final TextView name = root.findViewById(R.id.text_name);
        final TextView age = root.findViewById(R.id.text_age);
        final TextView sex = root.findViewById(R.id.text_sex);
        final TextView ta = root.findViewById(R.id.text_ta);
        final TextView fc = root.findViewById(R.id.text_fc);
        final TextView fr = root.findViewById(R.id.text_fr);
        final TextView temp = root.findViewById(R.id.text_temp);
        final TextView weight = root.findViewById(R.id.text_weight);
        final TextView height = root.findViewById(R.id.text_height);
        final TextView imc = root.findViewById(R.id.text_bmi);
        final TextView observations = root.findViewById(R.id.text_observations);

        medicalRecordsViewModel.getData().observe(getViewLifecycleOwner(), new Observer<MedicalRecord>() {
            @Override
            public void onChanged(@Nullable MedicalRecord data) {
                name.setText(data.getName());
                age.setText(data.getAge());
                sex.setText(data.getSex());
                ta.setText(data.getTa());
                fc.setText(data.getFc());
                fr.setText(data.getFr());
                temp.setText(data.getTemp());
                weight.setText(data.getWeight());
                height.setText(data.getHeight());
                imc.setText(data.getImc());
                observations.setText(data.getObservations());
            }
        });
        return root;
    }
}
