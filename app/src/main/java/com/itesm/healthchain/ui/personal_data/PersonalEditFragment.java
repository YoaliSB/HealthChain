package com.itesm.healthchain.ui.personal_data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.models.PersonalData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class PersonalEditFragment extends Fragment {

    private PersonalDataViewModel personalDataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personalDataViewModel =
                ViewModelProviders.of(getActivity()).get(PersonalDataViewModel.class);
        View root = inflater.inflate(R.layout.personal_edit_fragment, container, false);

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
        final Button saveButton = root.findViewById(R.id.save_btn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(getParentFragment()).navigateUp();
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
