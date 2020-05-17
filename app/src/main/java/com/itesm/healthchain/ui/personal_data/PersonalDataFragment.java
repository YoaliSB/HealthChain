package com.itesm.healthchain.ui.personal_data;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.data.model.PersonalData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class PersonalDataFragment extends Fragment {

    protected PersonalDataViewModel personalDataViewModel;
    protected View root;
    TextView name, age, blood, weight, height, contactName, contactPhone, contactRelationship, hospital, ailments, allergies;
    protected View fields, nfcScreen;
    private View phoneButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.personal_fragment, container, false);
        name = root.findViewById(R.id.text_name);
        age = root.findViewById(R.id.text_age);
        blood = root.findViewById(R.id.text_blood);
        weight = root.findViewById(R.id.text_weight);
        height = root.findViewById(R.id.text_height);
        contactName = root.findViewById(R.id.text_contact_name);
        contactPhone = root.findViewById(R.id.text_contact_phone);
        contactRelationship = root.findViewById(R.id.text_contact_relationship);
        hospital = root.findViewById(R.id.text_hospital);
        ailments = root.findViewById(R.id.list_ailments);
        allergies = root.findViewById(R.id.list_allergies);
        fields = root.findViewById(R.id.fields);
        phoneButton = root.findViewById(R.id.phone_btn);
        nfcScreen = root.findViewById(R.id.nfc_screen);

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.fromParts("tel", contactPhone.getText().toString(), null));
                startActivity(intent);
            }
        });

        personalDataViewModel.getData().observe(getViewLifecycleOwner(), new Observer<PersonalData>() {
            @Override
            public void onChanged(@Nullable PersonalData data) {
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
            }
        });
        return root;
    }
}
