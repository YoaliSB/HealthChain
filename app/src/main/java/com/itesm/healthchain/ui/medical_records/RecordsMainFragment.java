package com.itesm.healthchain.ui.medical_records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.healthchain.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class RecordsMainFragment extends Fragment implements View.OnClickListener{

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_records_fragment, container, false);

        View recordsButton = root.findViewById(R.id.btn_records);
        recordsButton.setOnClickListener(this);

        View prescriptionsButton = root.findViewById(R.id.btn_prescriptions);
        prescriptionsButton.setOnClickListener(this);

        View doctorsButton = root.findViewById(R.id.btn_doctors);
        doctorsButton.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_records:
                replaceFragment(R.id.navigation_medical_records);
                break;
            case R.id.btn_prescriptions:
                replaceFragment(R.id.navigation_prescriptions);
                break;
            case R.id.btn_doctors:
                replaceFragment(R.id.navigation_doctors);
        }
    }


    private void replaceFragment(int id) {
        NavHostFragment.findNavController(this).navigate(id);
    }
}

