package com.itesm.healthchain.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.healthchain.R;
import com.itesm.healthchain.ui.personal_data.PatientDataViewModel;
import com.itesm.healthchain.ui.personal_data.PersonalDataFragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

public class DoctorPatientDataFragment extends PersonalDataFragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        personalDataViewModel =
                ViewModelProviders.of(getActivity(),
                        new DoctorPatientDataViewModel.Factory(getActivity()))
                        .get(DoctorPatientDataViewModel.class);

        super.onCreateView(inflater, container, savedInstanceState);
        nfcScreen.setVisibility(View.GONE);
        fields.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.GONE);
        editButton2.setVisibility(View.GONE);
        return root;
    }
}
