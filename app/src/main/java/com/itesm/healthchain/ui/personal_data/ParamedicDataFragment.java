package com.itesm.healthchain.ui.personal_data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.healthchain.R;
import com.itesm.healthchain.data.model.PersonalData;
import com.itesm.healthchain.data.model.TagProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ParamedicDataFragment extends PersonalDataFragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        personalDataViewModel =
                ViewModelProviders.of(getActivity()).get(ParamedicDataViewModel.class);
        super.onCreateView(inflater, container, savedInstanceState);
        nfcScreen.setVisibility(View.VISIBLE);
        fields.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);
        editButton2.setVisibility(View.GONE);

        final View nfcScreen = root.findViewById(R.id.nfc_screen);
        personalDataViewModel.getData().observe(getViewLifecycleOwner(), new Observer<PersonalData>() {
            @Override
            public void onChanged(@Nullable PersonalData data) {
                if (data == null) {
                    nfcScreen.setVisibility(View.VISIBLE);
                    fields.setVisibility(View.GONE);
                } else {
                    name.setText(data.getName());
                    age.setText(data.getBirthDate());
                    blood.setText(data.getBlood());
                    weight.setText(data.getWeight());
                    height.setText(data.getHeight());
                    contactName.setText(data.getContactName());
                    contactPhone.setText(data.getContactPhone());
                    contactRelationship.setText(data.getContactRelationship());
                    hospital.setText(data.getHospital());
                    ailments.setText(data.getAilments());
                    allergies.setText(data.getAllergies());
                    nfcScreen.setVisibility(View.GONE);
                    fields.setVisibility(View.VISIBLE);
                }
            }
        });
        return root;
    }
}
