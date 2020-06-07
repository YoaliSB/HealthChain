package com.itesm.healthchain.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.itesm.healthchain.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class DoctorMenuFragment extends Fragment  implements View.OnClickListener{

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dr_menu_fragment, container, false);
        Button patientsBtn = root.findViewById(R.id.btn_patients);
        Button newPatientBtn = root.findViewById(R.id.btn_new_patient);
        patientsBtn.setOnClickListener(this);
        newPatientBtn.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_patients:
                replaceFragment(R.id.navigation_patients);
                break;
        }
    }

    private void replaceFragment(int id) {
        NavHostFragment.findNavController(this).navigate(id);
    }
}
