package com.itesm.healthchain.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.healthchain.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

public class PatientDetailMenuFragment extends Fragment implements View.OnClickListener{
    DoctorPatientDataViewModel personalDataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.patient_detail_menu, container, false);

        View patientInfo = root.findViewById(R.id.btn_personal_info);
        patientInfo.setOnClickListener(this);

        View recordsButton = root.findViewById(R.id.btn_records);
        recordsButton.setOnClickListener(this);

        View prescriptionsButton = root.findViewById(R.id.btn_prescriptions);
        prescriptionsButton.setOnClickListener(this);

        View newEntryButton = root.findViewById(R.id.btn_new_entry);
        newEntryButton.setOnClickListener(this);

        personalDataViewModel =
                ViewModelProviders.of(getActivity(),
                        new DoctorPatientDataViewModel.Factory(getActivity()))
                        .get(DoctorPatientDataViewModel.class);

        Bundle bundle = getArguments();

        if (bundle != null) {
            // Fire network request
            String email = bundle.getString("email");
            if (email != null) {
                personalDataViewModel.fetchPersonalData(email);
            }
        }
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_personal_info:
                replaceFragment(R.id.navigation_personal);
                break;
            case R.id.btn_records:
                replaceFragment(R.id.navigation_medical_records);
                break;
            case R.id.btn_prescriptions:
                replaceFragment(R.id.navigation_prescriptions);
                break;
            case R.id.btn_new_entry:
                replaceFragment(R.id.navigation_new_medical_entry);
        }
    }


    private void replaceFragment(int id) {
        NavHostFragment.findNavController(this).navigate(id);
    }
}