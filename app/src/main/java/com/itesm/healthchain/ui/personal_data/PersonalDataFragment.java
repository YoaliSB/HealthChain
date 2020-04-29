package com.itesm.healthchain.ui.personal_data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.models.PersonalData;
import com.itesm.healthchain.models.TagProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class PersonalDataFragment extends Fragment {

    private PersonalDataViewModel personalDataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personalDataViewModel =
                ViewModelProviders.of(getActivity()).get(PersonalDataViewModel.class);
        View root = inflater.inflate(R.layout.personal_fragment, container, false);

        final TextView name = root.findViewById(R.id.text_name);
        final TextView age = root.findViewById(R.id.text_age);
        final TextView blood = root.findViewById(R.id.text_blood);
        final TextView weight = root.findViewById(R.id.text_weight);
        final TextView height = root.findViewById(R.id.text_height);
        final TextView contactName = root.findViewById(R.id.text_contact_name);
        final TextView contactPhone = root.findViewById(R.id.text_contact_phone);
        final TextView contactRelationship = root.findViewById(R.id.text_contact_relationship);
        final TextView hospital = root.findViewById(R.id.text_hospital);
        final TextView ailments = root.findViewById(R.id.list_ailments);
        final TextView allergies = root.findViewById(R.id.list_allergies);
        final View nfcScreen = root.findViewById(R.id.nfc_screen);
        final View fields = root.findViewById(R.id.fields);

        nfcScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personalDataViewModel.setData(new PersonalData(new TagProfile()));
            }
        });

        personalDataViewModel.getData().observe(getViewLifecycleOwner(), new Observer<PersonalData>() {
            @Override
            public void onChanged(@Nullable PersonalData data) {
                if (data == null) {
                    // TODO: Call updateProfileState() -> check if data, show profile if not empty
                    // Include nfc fragment in layout and toggle it
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
