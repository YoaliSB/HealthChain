package com.itesm.healthchain.ui.personal_data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.healthchain.R;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

public class PatientDataFragment extends PersonalDataFragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        personalDataViewModel =
                ViewModelProviders.of(getActivity()).get(PatientPersonalDataViewModel.class);
        super.onCreateView(inflater, container, savedInstanceState);
        nfcScreen.setVisibility(View.GONE);
        fields.setVisibility(View.VISIBLE);
        final View.OnClickListener editPersonalData = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.navigation_personal_edit);
            }
        };
        final View editButton = root.findViewById(R.id.edit_btn);
        final View editButton2 = root.findViewById(R.id.edit_btn2);
        editButton.setOnClickListener(editPersonalData);
        editButton2.setOnClickListener(editPersonalData);
        return root;
    }
}
